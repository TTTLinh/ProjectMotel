package com.example.controller.admin;

import com.example.dto.TypeDTO;
import com.example.entities.Room;
import com.example.entities.Type;
import com.example.iservice.IRoom;
import com.example.iservice.IType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/types")
public class TypeController {

    @Autowired
    IType iType;
    @Autowired
    IRoom iRoom;

    @GetMapping("/add")
    public String add(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
        model.addAttribute("type", new TypeDTO());
        model.addAttribute("pageTitle","Create Type Room");
        return "type/form";
    }


    @GetMapping("edit/{typeId}")
    public ModelAndView edit(ModelMap model, @PathVariable(name = "typeId") Integer typeId,
                             HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }
        Optional<Type> op=iType.findById(typeId);
        TypeDTO typeDTO=new TypeDTO();
        if (op.isPresent()){ //nếu tồn tại thì lấy thông tin củe entity đưa qua cho dto map
            Type entity=op.get();
            BeanUtils.copyProperties(entity,typeDTO);
            model.addAttribute("type", typeDTO);
            model.addAttribute("pageTitle","Edit Type Room Id: "+typeId);
            typeDTO.setEdit(true);
            return new ModelAndView("type/form",model);
        }
        model.addAttribute("message","Type is not existed");
        return new ModelAndView("forward:/admin/types", model);
    }


    @PostMapping("save")
    public ModelAndView save(ModelMap model,
                       @Valid @ModelAttribute("type") TypeDTO typeDTO,
                       BindingResult result,
                             HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }
        if (result.hasErrors()){
            return new ModelAndView("type/form");
        }
        Type entity=new Type();
        BeanUtils.copyProperties(typeDTO,entity); //thực hiện gọi phương thức copyproprtties để copy đối tượng dto sang entity
        iType.save(entity); // sau đó lưu thông tin entity
        model.addAttribute("message","Save type room successfully");
        return new ModelAndView("forward:/admin/types", model); //trả về phương thức types và truyền vào đối tượng model
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

        List<Type> list=null;
        if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
            list=iType.findByNameContaining(name);
        }else {
            list=iType.findAll();
        }
        model.addAttribute("type",list);
        return "type/list";
    }

    @GetMapping(value = "delete/{typeId}")
    public String delete(@PathVariable(name = "typeId") Integer typeId, ModelMap model,
                         HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }

        try {
            List<Room> roomtype= iRoom.findByType(typeId);
            iRoom.deleteAll(roomtype);
            iType.deleteById(typeId);
            model.addAttribute("message","Delete Type ID: "+typeId+ "successfully");
            return "forward:/admin/types";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



//
//    @GetMapping(path = "/searchTypeAndPrice")
//    public String searchTypeAndPrice(ModelMap model,
//                                     @RequestParam(name = "type", required = false) Type type,
//                                     @RequestParam(name = "price", required = false) Boolean price){
//
//
//        return "type/search";
//    }

    //    @GetMapping(path="/searchpage")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name,// xác định giá trị có name or k có
//                         @RequestParam("page") Optional<Integer> page,
//                         @RequestParam("size") Optional<Integer> size){ // lấy giả trị page và size
//
//
//            int currentPage= page.orElse(1);   //nếu người dùng k nhâp gía trị thì ngầm định là 1
//            int pageSize=size.orElse(5); // ngầm định size=5
//        Pageable pageable= PageRequest.of(currentPage-1,pageSize, Sort.by("name")); //thực hiện yê ầu tryền vào trang hiện tại  và size
//        Page<Type> resultPage=null;
//
//
//        if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
//            resultPage=iType.findByNameContaining(name, pageable);
//            model.addAttribute("name", name);  // trả lại dữa liệu
//        }else {
//            resultPage=iType.findAll(pageable);
//        }
//         //Số trang hiển tthi trên view
//        int totaPages =resultPage.getTotalPages(); //trả về các trang đã đc phân trang
//        if (totaPages>0){
//            int start=Math.max(1, currentPage-2);  //hiển thị 2 nên trừ 2
//            int end=Math.min(currentPage+2,totaPages);
//            if (totaPages>5){
//                if (end==totaPages) start=end-5;
//                else if (start==1) end=start+5;
//            }
//            List<Integer> pagenumber= IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList()); //xác đinh số trang sẽ đc sinh ra
//            model.addAttribute("pageNumber", pagenumber);
//        }
//
//        model.addAttribute("typePage",resultPage);
//        return "admin/types/searchpage";
//    }

}
