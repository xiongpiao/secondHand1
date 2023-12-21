package com.tumiao.service.serviceImpl.ManagerOperationImpl;

import com.tumiao.dao.ManagerDao;
import com.tumiao.entity.Orders;
import com.tumiao.entity.Product_info;
import com.tumiao.entity.User;
import com.tumiao.pojo.Manager_order_info;
import com.tumiao.service.serviceInterface.ManagerOperation.ManagerService;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService
{
    @Autowired
    ManagerDao managerDao;
    @Override
    public ReturnInfo managerLogin(HttpServletResponse response, String manager_name, String password)
    {
        String name = managerDao.getName(manager_name,password);
        if (name==null)
        {
            return new ReturnInfo("500","登录失败！用户名或密码错误！");
        }
        else
        {
            String encode = JwtUtils.encode(manager_name, password);
            response.setHeader(encode,"manager_token");
            return new ReturnInfo(encode,"200","登录成功！");
        }
    }

    @Override
    public ReturnInfo getAllUserInfo() {
        List<User> users = managerDao.gerUserInfos();
        return new ReturnInfo(users,"200","查询成功！");
    }

    @Override
    public ReturnInfo updatePointUserInfoByUserName(String username,String new_password) {
        try {
            managerDao.updatePointUserInfo(username, new_password);
            return new ReturnInfo("200","修改密码成功！");
        }catch (Exception e)
        {
            return new ReturnInfo("500","修改密码失败！");
        }
    }

    @Override
    public ReturnInfo getUserInfo(String username) {
        User user = managerDao.gerUserInfo(username);
        if(user==null)
            return new ReturnInfo("500","未查询到当前用户！");
        return new ReturnInfo(user,"200","查询成功!");
    }

    @Override
    public ReturnInfo getAllProductInfoNotBePurchased() {
        List<Product_info> product_infos = managerDao.getProductInfosNotBePurchased();
        return new ReturnInfo(product_infos,"200","信息查询成功！");
    }

    @Override
    public ReturnInfo deletePointProductInfoByProductNumber(String productNumber) {
        try {
            String flag = managerDao.getBePurchased(productNumber);
            if (flag == null)
                return new ReturnInfo("500","当前商品不存在或已经被购买！无法删除！");
            managerDao.deletePoint(productNumber);
            return new ReturnInfo("200","信息删除成功！");
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ReturnInfo("500","删除信息失败!");
        }
    }

    @Override
    public ReturnInfo getAllOrderInfo() {
        List<Manager_order_info> manager_order_infos = new ArrayList<>();
        List<Orders> orders = managerDao.gerOrders();
        for (int i = 0;i<orders.size();i++)
        {
            Orders order = orders.get(i);
            String product_number = order.getProduct_number();
            if(product_number==null||product_number.length()==0)
                return new ReturnInfo("500","订单查询失败!商品号不能为空！");
            Integer price = managerDao.getPrice(product_number);
            Manager_order_info manager_order_info = new Manager_order_info();
            manager_order_info.setOrder_number(order.getOrder_number());
            manager_order_info.setIs_get(order.getIs_get());
            manager_order_info.setIs_put(order.getIs_put());
            manager_order_info.setPrice(price);
            manager_order_infos.add(manager_order_info);
        }
        return new ReturnInfo(manager_order_infos,"200","订单信息查询成功！");
    }

    @Override//2. 根据订单号得到订单信息//订单号+价格+是否发货+是否收货
    public ReturnInfo getPointOrderInfo(String orderNumber) {
        Orders order = managerDao.getOrder(orderNumber);
        if (order == null)
            return new ReturnInfo("500","该订单号不存在！");
        String product_number = order.getProduct_number();
        if(product_number == null)
            return new ReturnInfo("500","查询失败！商品号不能为空！");

        Integer price = managerDao.getPrice(product_number);
        Manager_order_info manager_order_info = new Manager_order_info();
        manager_order_info.setOrder_number(orderNumber);
        manager_order_info.setPrice(price);
        manager_order_info.setIs_get(order.getIs_get());
        manager_order_info.setIs_put(order.getIs_put());
        return new ReturnInfo(manager_order_info,"200","查询成功！");
    }


}
