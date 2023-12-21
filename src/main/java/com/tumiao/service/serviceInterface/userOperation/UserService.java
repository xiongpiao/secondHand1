package com.tumiao.service.serviceInterface.userOperation;

import com.tumiao.utils.ReturnInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {

    //用户登录
    ReturnInfo login(HttpServletResponse response,String username, String password);

    //注册学生用户
    ReturnInfo register(String username,String password);

}
