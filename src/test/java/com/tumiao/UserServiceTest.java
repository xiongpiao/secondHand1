package com.tumiao;

import com.tumiao.dao.TestDao;
import com.tumiao.dao.UserDao;
import com.tumiao.service.serviceInterface.userOperation.UserService;
import com.tumiao.utils.ReturnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    TestDao testDao;
    @Test
    public void testLogin()
    {
//        ReturnInfo yqqt23 = userService.login("yqqt23", "222");
//        System.out.println(yqqt23.isFlag());
    }
    @Test
    public void testRegister()
    {
        ReturnInfo register = userService.register("测试数据2", "测试数据2");
        System.out.println(register.getCode()+register.getMsg());
    }
    @Test
    public void getUser()
    {
        List<String> usernames = testDao.getUsernames();
        System.out.println(usernames);
    }

}
