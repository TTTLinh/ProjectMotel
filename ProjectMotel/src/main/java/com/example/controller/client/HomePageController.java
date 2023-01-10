package com.example.controller.client;

import com.example.dto.RoomDTO;
import com.example.entities.Room;
import com.example.entities.Type;
import com.example.iservice.IRoom;
import com.example.iservice.IStorage;
import com.example.iservice.IType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("client")
public class HomePageController {
    @Autowired  IType iType;

    @Autowired  IRoom iRoom;

    @Autowired  IStorage iStorage;

         @GetMapping("home")
        public String home(ModelMap model, HttpServletRequest request,
                           @RequestParam(name="name", required = false) String name,
                           @RequestParam(name = "type", required = false) Integer typeId,
                           @RequestParam(name = "price",required = false) Double price){
             List<Type> types = iType.findAll();
//             List<Room> rooms= iRoom.findAll();
             List<RoomDTO> roomDTOs = new ArrayList<>();
             List<Room> rooms=null;
             if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
                 rooms=iRoom.findByNameContaining(name);
             }else if (typeId!=null && price!=null) {
                 if (price>=2000000){
                     rooms = iRoom.findByTypeAndPrice1(typeId, price);
                 }else {
                     rooms = iRoom.findByTypeAndPrice2(typeId, price);
                 }

             }else if (typeId!=null && price==null){
                 rooms=iRoom.findByType(typeId);
             }else if (typeId==null && price!=null){
                 if (price>=2000000){
                     rooms=iRoom.findByPrice1(price);
                 }else {
                     rooms=iRoom.findByPrice2(price);
                 }
             }
             else {
                 rooms = iRoom.findAll();
             }
             if (!rooms.isEmpty()) {
                 rooms.forEach(ele -> {
                     RoomDTO roomDTO=new RoomDTO();
                     BeanUtils.copyProperties(ele,roomDTO);
                     roomDTO.setTypeName(ele.getType().getName());
                     roomDTO.setImageSrc(ele.getImage());
                     roomDTOs.add(roomDTO);
                 });
             }

             request.getSession().getAttribute("userLogin");
             model.addAttribute("rooms",roomDTOs);
             model.addAttribute("types", types);
        return "index";
        }


        @GetMapping("/images/{filename:.+}")
        @ResponseBody
        public ResponseEntity<Resource> serveFile(@PathVariable String filename){
            Resource file=iStorage.loadAsResource(filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\""+file.getFilename()+"\"").body(file);
        }

//        @GetMapping(path="/search")
//        public String search(ModelMap model, HttpServletRequest request,
//                             @RequestParam(name="name", required = false) String name){ // xác định giá trị có name or k có
//            List<Type> types = iType.findAll();
//            request.getSession().getAttribute("userLogin");
//             List<Room> list=null;
//            if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
//                list=iRoom.findByNameContaining(name);
//            } else {
//                list=iRoom.findAll();
//            }
//            model.addAttribute("rooms",list);
//            model.addAttribute("types",types);
//            return "index";
//        }

    @GetMapping(path="views/{roomId}")
    public String view(ModelMap model, @PathVariable(name = "roomId") Integer roomId,
                       HttpServletRequest request){
        request.getSession().getAttribute("userLogin");
        Optional<Room> room=iRoom.findById(roomId);
        RoomDTO roomDTO=new RoomDTO();
        if (room.isPresent()){ //nếu tồn tại thì lấy thông tin củe entity đưa qua cho dto map
            Room entity=room.get();
            BeanUtils.copyProperties(entity,roomDTO);
            roomDTO.setTypeId(entity.getType().getTypeId());
            roomDTO.setImageSrc(entity.getImage());
            roomDTO.setTypeName(entity.getType().getName());
            model.addAttribute("room", roomDTO);
            roomDTO.setEdit(true);
            return "detail";
        }
        return "forward:/client/home";
    }

}
