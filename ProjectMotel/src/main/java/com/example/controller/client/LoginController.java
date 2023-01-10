package com.example.controller.client;

import com.example.dto.UserDTO;
import com.example.iservice.IRoom;
import com.example.iservice.IType;
import com.example.iservice.IUser;
import com.example.utils.EnumRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("client")
public class LoginController {

    @Autowired
    IUser iUser;
    @Autowired
    IType iType;
    @Autowired
    IRoom iRoom;



    @RequestMapping("/login")
    public String login(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") != null) {
            return "redirect:home";
        }
        model.addAttribute("user", new UserDTO());
        return "user/login";
    }


    @GetMapping ("/logout")
    public String logout(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") != null) {
            request.getSession().setAttribute("userLogin", null);
        }
        return "redirect:home";
    }



    @PostMapping("/login")
    public String login(ModelMap model, @ModelAttribute("user") UserDTO userDTO,
                        BindingResult result, HttpServletRequest request){
        var user = iUser.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());

        if (user.isPresent() && user.get().getRole().equals(EnumRole.USER)) {
            request.getSession().setAttribute("userLogin",user);
            return "redirect:home";
//            redirect:
        }else if (user.isPresent()&&user.get().getRole().equals(EnumRole.ADMIN)){
            model.addAttribute("message","Vui lòng đăng nhập trang admin");
        }else {
        model.addAttribute("message","tài khoản không tồn tại");}
        return "user/login";

    }



//
//
//    @GetMapping(path="/search")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name){ // xác định giá trị có name or k có
//        List<Type> list=null;
//        if (StringUtils.hasText(name)){  // kiểu tra tên tryền về có giống nôi dung
//            list=iType.findByNameContaining(name);
//        }else {
//            list=iType.findAll();
//        }
//        model.addAttribute("types",list);
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
