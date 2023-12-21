package com.tumiao.controller;

import com.tumiao.service.serviceInterface.userOperation.UserService;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/UserService")
//注册登录
public class UserController {

    @Autowired
    UserService userService;
    //用户登录
    @PostMapping("/UserLogin")
    public ReturnInfo userLogin(HttpServletResponse response,@RequestParam String username, @RequestParam String password) {
        if ( username == null||password == null)
        {
            return new ReturnInfo("500","用户名或密码输入不能为空！");
        }
        return userService.login(response,username,password);
    }
    //用户注册
    @PostMapping("/UserRegister")
    public ReturnInfo userRegister(@RequestParam String username,@RequestParam String password)
    {
        if(username==null||username.length()<=0||password==null||password.length()<=0)
        {
            return new ReturnInfo("500","用户名或密码输入不能为空！");
        }
        if(username.length()>12)
        {
            return new ReturnInfo("500","用户名长度不能大于12");
        }
        if(password.length()>16)
        {
            return new ReturnInfo("500","密码长度不能大于16");
        }
        return userService.register(username,password);
    }
}
