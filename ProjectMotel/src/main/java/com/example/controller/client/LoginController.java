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
            model.addAttribute("message","Vui l??ng ????ng nh???p trang admin");
        }else {
        model.addAttribute("message","t??i kho???n kh??ng t???n t???i");}
        return "user/login";

    }



//
//
//    @GetMapping(path="/search")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name){ // x??c ?????nh gi?? tr??? c?? name or k c??
//        List<Type> list=null;
//        if (StringUtils.hasText(name)){  // ki???u tra t??n try???n v??? c?? gi???ng n??i dung
//            list=iType.findByNameContaining(name);
//        }else {
//            list=iType.findAll();
//        }
//        model.addAttribute("types",list);
//        return "type/search";
//    }


    //    @GetMapping(path="/searchpage")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required = false) String name,// x??c ?????nh gi?? tr??? c?? name or k c??
//                         @RequestParam("page") Optional<Integer> page,
//                         @RequestParam("size") Optional<Integer> size){ // l???y gi??? tr??? page v?? size
//
//
//            int currentPage= page.orElse(1);   //n???u ng?????i d??ng k nh??p g??a tr??? th?? ng???m ?????nh l?? 1
//            int pageSize=size.orElse(5); // ng???m ?????nh size=5
//        Pageable pageable= PageRequest.of(currentPage-1,pageSize, Sort.by("name")); //th???c hi???n y?? ???u try???n v??o trang hi???n t???i  v?? size
//        Page<Type> resultPage=null;
//
//
//        if (StringUtils.hasText(name)){  // ki???u tra t??n try???n v??? c?? gi???ng n??i dung
//            resultPage=iType.findByNameContaining(name, pageable);
//            model.addAttribute("name", name);  // tr??? l???i d???a li???u
//        }else {
//            resultPage=iType.findAll(pageable);
//        }
//         //S??? trang hi???n tthi tr??n view
//        int totaPages =resultPage.getTotalPages(); //tr??? v??? c??c trang ???? ??c ph??n trang
//        if (totaPages>0){
//            int start=Math.max(1, currentPage-2);  //hi???n th??? 2 n??n tr??? 2
//            int end=Math.min(currentPage+2,totaPages);
//            if (totaPages>5){
//                if (end==totaPages) start=end-5;
//                else if (start==1) end=start+5;
//            }
//            List<Integer> pagenumber= IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList()); //x??c ??inh s??? trang s??? ??c sinh ra
//            model.addAttribute("pageNumber", pagenumber);
//        }
//
//        model.addAttribute("typePage",resultPage);
//        return "admin/types/searchpage";
//    }

}
