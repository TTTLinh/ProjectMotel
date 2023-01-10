package com.example.controller.admin;

import com.example.dto.UserDTO;
import com.example.entities.User;
import com.example.iservice.IUser;
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
@RequestMapping("admin/users")
public class UserController {

    @Autowired
    IUser iUser;

    @GetMapping("add")
    public String add(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
        model.addAttribute("user", new UserDTO());
        return "user/form";
    }


    @GetMapping("edit/{userId}")
    public ModelAndView edit(ModelMap model,
                             @PathVariable(name = "userId") Integer userId,
                             HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }
        Optional<User> op=iUser.findById(userId);
        UserDTO userDTO=new UserDTO();
        if (op.isPresent()){ //nếu tồn tại thì lấy thông tin củe entity đưa qua cho dto map
            User entity=op.get();
            BeanUtils.copyProperties(entity,userDTO);
            model.addAttribute("user", userDTO);
            model.addAttribute("pageTitle","Edit User  Id: "+userId);
            userDTO.setEdit(true);
            return new ModelAndView("user/form",model);
        }
        model.addAttribute("message","User is not existed");
        return new ModelAndView("forward:/admin/users", model);
    }
//

    @PostMapping("save")
    public ModelAndView save(ModelMap model,
                       @Valid @ModelAttribute("user") UserDTO userDTO,
                       BindingResult result, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return new ModelAndView("forward:/admin/login", model);
        }
        if (result.hasErrors()){
            model.addAttribute("message","registration failed");
            return new ModelAndView("user/form", model);
        }else if (userDTO.getUserId()==null){
            if (iUser.findByEmailContaining(userDTO.getEmail()).isEmpty()==false) {
                model.addAttribute("message", "email đã tồn tại");
                return new ModelAndView("user/form", model);
            }
        }
        User entity=new User();
        BeanUtils.copyProperties(userDTO,entity); //thực hiện gọi phương thức copyproprtties để copy đối tượng dto sang entity
        iUser.save(entity); // sau đó lưu thông tin entity
        model.addAttribute("message","Register successfully");
        return new ModelAndView("forward:/admin/users", model); //trả về phương thức types và truyền vào đối tượng model
        //chuyển hướng và chuyển hướng ở phía server và chuyển kèm cái yêu cầu tới cho cái list
    }




    @GetMapping(value = "delete/{userId}")
    public String delete(@PathVariable(name = "userId") Integer userId, ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn chưa đăng nhập");
            return "login";
        }
        try {
            iUser.deleteById(userId);
            model.addAttribute("message","Delete User ID: "+userId+ "successfully");
            return "forward:/admin/users";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    @RequestMapping("")
    public String list(ModelMap model,
                       @RequestParam(name="email", required = false) String email, HttpServletRequest request){


        if (request.getSession().getAttribute("userLogin") == null) {
            model.addAttribute("message", "Bạn phải đăng nhập");
            return "login";
        }
        List<User> list=null;
        if (StringUtils.hasText(email)){  // kiểu tra tên tryền về có giống nôi dung
            list=iUser.findByEmailContaining(email);
        }else {
          list=iUser.findAll();
        }
        model.addAttribute("user",list);
        return "user/list";
    }


//
////
//    @GetMapping(path="/search")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name){ // xác định giá trị có name or k có
//        List<User> list=null;
//        if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
//            list=iUser.findByNameContaining(name);
//        }else {
//            list=iUser.findAll();
//        }
//        model.addAttribute("user",list);
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
