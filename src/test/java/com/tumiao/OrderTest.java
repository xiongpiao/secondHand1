package com.tumiao;

import com.tumiao.dao.OrderDao;
import com.tumiao.entity.Orders;
import com.tumiao.service.serviceInterface.userOperation.OrderService;
import com.tumiao.utils.ReturnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderTest {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderService orderService;
    @Test
    public void test1()
    {
        List<Orders> xiuyingyu = orderDao.getOrders("zql");
        System.out.println(xiuyingyu);
    }
    @Test
    public void  test2()
    {
        ReturnInfo zql = orderService.getAllPurchaseOrders("zql");
        System.out.println(zql.getData());
    }
    @Test
    public void test3()
    {
        orderService.isPutProduct("zql","order_1671613761899ZYCDPI");
    }
}
