package com.example.controller.client;

import com.example.dto.UserDTO;
import com.example.entities.User;
import com.example.iservice.IUser;
import com.example.utils.EnumRole;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("client/users")
public class UserRegistesController {
    @Autowired private IUser iUser;

    @PostMapping("save")
    public ModelAndView save(ModelMap model,
                             @Valid @ModelAttribute("user") UserDTO userDTO,
                             BindingResult result){
        if (result.hasErrors()){
            model.addAttribute("message","Đăng ký thất bại");
            return new ModelAndView("user/login", model);
        }
        if(iUser.findByEmailContaining(userDTO.getEmail()).isEmpty()==false){
            model.addAttribute("message","Email đã tồn tại");
            return new ModelAndView("user/login", model);
        }
        User entity=new User();
        BeanUtils.copyProperties(userDTO,entity);
        entity.setRole(EnumRole.valueOf("USER"));//thực hiện gọi phương thức copyproprtties để copy đối tượng dto sang entity
        iUser.save(entity); // sau đó lưu thông tin entity
        model.addAttribute("messageSuccessfully","Đăng ký thành công");
        return new ModelAndView("user/login", model); //trả về phương thức types và truyền vào đối tượng model
        //chuyển hướng và chuyển hướng ở phía server và chuyển kèm cái yêu cầu tới cho cái list
    }




}
