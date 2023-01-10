package com.example.controller.admin;


import com.example.dto.RoomDTO;
import com.example.dto.TypeDTO;
import com.example.entities.Room;
import com.example.entities.Type;
import com.example.iservice.IRoom;
import com.example.iservice.IStorage;
import com.example.iservice.IType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("admin/rooms")
public class RoomController {
    @Autowired
    IType iType;

    @Autowired
    IRoom iRoom;

    @Autowired
    IStorage iStorage;

    @ModelAttribute("types")
    public List<TypeDTO> getTypes(){
        return iType.findAll().stream().map(x->{
            TypeDTO typeDTO =new TypeDTO();
            BeanUtils.copyProperties(x,typeDTO);
            return typeDTO;}).toList();
    }

    @GetMapping("add")
    public String add(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
        model.addAttribute("room", new RoomDTO());
        model.addAttribute("pageTitle","Create Room");
        return "room/form";
    }


    @GetMapping(path="edit/{roomId}")
    public ModelAndView edit(ModelMap model,
                             @PathVariable(name = "roomId") Integer roomId,
                             HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }
        Optional<Room> op=iRoom.findById(roomId);
        RoomDTO roomDTO=new RoomDTO();
        if (op.isPresent()){ //nếu tồn tại thì lấy thông tin củe entity đưa qua cho dto map
            Room entity=op.get();
            BeanUtils.copyProperties(entity,roomDTO);
            roomDTO.setTypeId(entity.getType().getTypeId());
            roomDTO.setImageSrc(entity.getImage());
            model.addAttribute("room", roomDTO);
            model.addAttribute("pageTitle","Edit Room Id: "+roomId);
            roomDTO.setEdit(true);
            return new ModelAndView("room/form",model);
        }
        model.addAttribute("message","Room is not existed");
        return new ModelAndView("forward:/admin/rooms", model);
    }


    @PostMapping(value = "/save")
    public ModelAndView save(ModelMap model,
                             @Valid @ModelAttribute("room") RoomDTO roomDTO,
                             BindingResult result, HttpServletRequest request){  // kiểu dữ liệu trả về và kiểu model là Map
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }

        if (result.hasErrors()){
            return new ModelAndView("room/form");
        }
        Room entity=new Room();
        BeanUtils.copyProperties(roomDTO,entity); //thực hiện gọi phương thức copyproprtties để copy đối tượng dto sang entity


        //xuwr lys rieng cho type, thêm thông tin type và room
        //Thêm thông tin type vào room type
        Type type=new Type();
        type.setTypeId(roomDTO.getTypeId());
        entity.setType(type);

//        xử lý nội dung hình up lên server và đc lưu trữ tên của hình trong phần inmage up
        if (!roomDTO.getImage().isEmpty()){
            UUID uuid=UUID.randomUUID();
            String uuString=uuid.toString();

            entity.setImage(iStorage.getStoredFilename(roomDTO.getImage(), uuString)); //xác định id cần lấy
            iStorage.store(roomDTO.getImage(), entity.getImage());  //xác định tn file cần lưu trữu ntn
        }
        iRoom.save(entity); // sau đó lưu thông tin entity
        model.addAttribute("message","Save  room successfully");
        return new ModelAndView("forward:/admin/rooms", model); //trả về phương thức types và truyền vào đối tượng model
        //chuyển hướng và chuyển hướng ở phía server và chuyển kèm cái yêu cầu tới cho cái list
    }

    @RequestMapping("")
    public String list(ModelMap model,
                       @RequestParam(name="name", required = false) String name,
                       HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
//        List<Room> list = iRoom.findAll();
//        model.addAttribute("rooms",list);
//        return "room/list";
         // xác định giá trị có name or k có
            List<Room> list=null;
            if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
                list=iRoom.findByNameContaining(name);
            }else {
                list=iRoom.findAll();
            }
            model.addAttribute("rooms",list);
            return "room/list";
        }



    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file=iStorage.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+file.getFilename()+"\"").body(file);
    }


    @GetMapping(value = "delete/{roomId}")
    public String delete(@PathVariable(name = "roomId") Integer roomId, ModelMap model,
                         HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }

        try {
            iRoom.deleteById(roomId);

            model.addAttribute("message","Delete Room ID: "+roomId+ "successfully");
            return "forward:/admin/rooms";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @GetMapping(path="/search")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name){ // xác định giá trị có name or k có
//        List<Room> list=null;
//        if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
//            list=iRoom.findByNameContaining(name);
//        }else {
//            list=iRoom.findAll();
//        }
//        model.addAttribute("rooms",list);
//        return "room/search";
//    }

    @GetMapping(path="view/{roomId}")
    public String view(ModelMap model, @PathVariable(name = "roomId") Integer roomId,
                       HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
        Optional<Room> op=iRoom.findById(roomId);
        RoomDTO roomDTO=new RoomDTO();
        if (op.isPresent()){ //nếu tồn tại thì lấy thông tin củe entity đưa qua cho dto map
            Room entity=op.get();
            BeanUtils.copyProperties(entity,roomDTO);
            roomDTO.setTypeId(entity.getType().getTypeId());
            roomDTO.setImageSrc(entity.getImage());
            roomDTO.setTypeName(entity.getType().getName());
            model.addAttribute("room", roomDTO);
            model.addAttribute("pageTitle","Edit Room Id: "+roomId);
            roomDTO.setEdit(true);
            return "room/detail";
        }
        return "forward:/admin/rooms";
    }
}
