package com.tumiao;

import com.tumiao.dao.ManagerDao;
import com.tumiao.entity.Product_info;
import com.tumiao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ManagerTest {
    @Autowired
    ManagerDao managerDao;

    @Test
    public void test1()
    {
        List<User> users = managerDao.gerUserInfos();
        System.out.println(users);
    }

    @Test
    public void test2()
    {
        List<Product_info> productInfosNotBePurchased = managerDao.getProductInfosNotBePurchased();
        System.out.println(productInfosNotBePurchased);
    }
}
