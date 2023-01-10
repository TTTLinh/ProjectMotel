package com.example.controller.admin;

import com.example.dto.UserDTO;
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
@RequestMapping("admin")
public class LoginAdminController {

    @Autowired
    IUser iUser;

    @GetMapping("login")
    public String login(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") != null) {
            return "redirect:/admin/users";
        }
        return "login";
    }

    @PostMapping("login")
    public String login(ModelMap model, @ModelAttribute("user") UserDTO userDTO,
                        BindingResult result, HttpServletRequest request){
        var user = iUser.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());

        if (user.isPresent() && user.get().getRole().equals(EnumRole.ADMIN)) {
            request.getSession().setAttribute("userLogin",user);
            return "redirect:/admin/users";
//            redirect:
        }else if (user.isPresent()&& user.get().getRole().equals(EnumRole.USER)){
            model.addAttribute("message","Bạn không có quyền truy cập ở trang này");
            return "login";
        }
        model.addAttribute("message","Kiểm tra lại email and password");
        return "login";

    }

    @PostMapping("/logout")
    public String logout(ModelMap model, HttpServletRequest request){
        if (request.getSession().getAttribute("userLogin") != null) {
            request.getSession().setAttribute("userLogin", null);
        }
        return "redirect:login";
    }

//    @PostMapping(path = "/login")
//    public ModelAndView loginWithValidationManual(
//            @Valid @ModelAttribute(name = "userDTO") UserDTO userDTO,
//            BindingResult errors,
//            Model model,
//            HttpServletRequest request)
//    {
//        if (userDTO.getEmail().isEmpty()) {
//            errors.rejectValue("name", "userDTO", "Please enter username");
//        }
//        if (userDTO.getPassword().isEmpty()) {
//            errors.rejectValue("password", "userDTO", "Please enter password");
//        }
//
//        return bindingModelLogin(userDTO, errors, model, request);
//    }
//
//    private ModelAndView bindingModelLogin(@ModelAttribute(name = "userDTO") UserDTO userDTO,
//                                           BindingResult errors,
//                                           Model model,
//                                           HttpServletRequest request)
//    {
//        if (errors.hasErrors())
//        {
//            return new ModelAndView("login");
////            return "login";
//        }
//
//        UserDTO users = iUser.findByUsernameAndPassword(userDTO.getEmail(), userDTO.getPassword());
//        AtomicReference<String> directory = new AtomicReference<>("login-page");
//
//        Optional.ofNullable(users).ifPresentOrElse(
//                x -> {
//                    directory.set("redirect:/products");
//                    HttpSession session = request.getSession();
//                    session.setAttribute(MappingUtils.SESSION_NAME, users);
//                },
//                () -> model.addAttribute("message", MappingUtils.MESSAGE_ERROR));
//
//        return new ModelAndView(directory.get());
////        return directory.get();
//    }
//

}
