package cn.web.auction.controller;

import cn.web.auction.pojo.Auctionuser;
import cn.web.auction.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String doLogin(String username, String userpassword,
                          String inputCode, HttpSession session, Model model) {
        //判定验证码
        if (!inputCode.equals(session.getAttribute("numrand"))) {
            model.addAttribute("erroMsg", "验证码不正确");
            return "login";
        }
        //查询数据库，查找用户
        Auctionuser user = userService.login(username, userpassword);
        if (user != null) {
            session.setAttribute("admin", user);
            return "redirect:/auction/findAuction";//登录成功后重定向到拍卖页面
        } else {
            model.addAttribute("erroMsg", "用户名或密码错误");
            return "login";
        }
    }
//    注销
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

//    注册
    @RequestMapping("/register")
    public String register(Model model,
                           @ModelAttribute(value = "registerUser") @Validated Auctionuser registerUser, BindingResult bindingResult){//BindingResult是错误信息的容器
        if(bindingResult.hasErrors()){//判定容器中是否有错误信息
// 方式一：
//            List<ObjectError> errorList= bindingResult.getAllErrors();
//            for(ObjectError error:errorList){
//                //获取在配置文件中编写的错误
//                System.out.println(error.getDefaultMessage());
//            }
//            //把list传给前端javaScript
//            model.addAttribute("errorList",errorList);
//  end
//  方式二：
            List<FieldError> errorList=bindingResult.getFieldErrors();
            for(FieldError fieldError:errorList){
                model.addAttribute(fieldError.getField(),fieldError.getDefaultMessage());
            }
//  end
            return "register";
        }

        //添加用户数据到数据库
        userService.addUser(registerUser);
        return "login";//注册成功
    }
}
