package com.tumiao.service.serviceImpl.userOperationImpl;

import com.tumiao.dao.UserDao;
import com.tumiao.service.serviceInterface.userOperation.UserService;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public ReturnInfo login(HttpServletResponse response,String username, String password) {
        boolean flag = judge(username,password);
        if(flag)//如果用户名或密码正确
        {
            String encode = JwtUtils.encode(username, password);
            return new ReturnInfo(encode,"200","登录成功！");
        }
        else
        {
            return new ReturnInfo("500","用户名或密码错误！");
        }
    }

    boolean judge(String username, String password) {

        return userDao.getUsername(username, password) != null;
    }

    @Override
    public ReturnInfo register(String username, String password) {
        //查询用户名是否重复
        String code = "";
        try {
            userDao.registerUser(username,password);
        }
        catch (DuplicateKeyException e)
        {
            return new ReturnInfo("500","用户名输入重复！");
        }
        return new ReturnInfo("200","注册成功");
    }
}
