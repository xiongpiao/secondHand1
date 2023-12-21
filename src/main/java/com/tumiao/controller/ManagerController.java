package com.tumiao.controller;

import com.tumiao.service.serviceInterface.ManagerOperation.ManagerService;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ManagerController")
public class ManagerController {//管理员接口
    @Autowired
    ManagerService managerService;
    //登录
    //管理员登录
    @PostMapping("/Login")
    public ReturnInfo managerLogin(HttpServletResponse response, @RequestParam String manager_name, @RequestParam String password)
    {
        if(manager_name==null||manager_name.length()==0||password == null||password.length()==0)
        {
            return new ReturnInfo("500","用户名或密码不能为空！");
        }
        return managerService.managerLogin(response,manager_name,password);
    }

    //管理用户信息
    //1. 查看所有用户信息
    @GetMapping("/GetUserInfo")
    public ReturnInfo getAllUserInfo()
    {
        return managerService.getAllUserInfo();
    }
    //2. 修改指定用户的密码
    @PostMapping("/UpdateUserPassword")
    public ReturnInfo updatePointUserInfoByUserName(@RequestParam String username
            ,@RequestParam String new_password)
    {
        if(username==null||username.length()==0||new_password==null||new_password.length()==0)
        {
            return new ReturnInfo("500","用户名或密码不能为空！");
        }
        return managerService.updatePointUserInfoByUserName(username,new_password);
    }
    //3. 根据用户名搜索用户信息
    @PostMapping("/GetUserInfoByUsername")
    public ReturnInfo getUserInfo(@RequestParam String username)
    {
        if (username == null || username.length()==0)
        {
            return new ReturnInfo("500","用户名输入不能为空！");
        }
        return managerService.getUserInfo(username);
    }
    //管理商品信息
    //1. 查看所有未被购买的商品信息
    @GetMapping("/ProductInfos")
    public ReturnInfo getAllProductInfoNotBePurchased()
    {
        return managerService.getAllProductInfoNotBePurchased();
    }
    //2. 删除指定商品信息
    @PostMapping("/DeletePointProductInfoB")
    public ReturnInfo deletePointProductInfoByProductNumber(@RequestParam String productNumber)
    {
        if(productNumber == null|| productNumber.length() == 0)
        {
            return new ReturnInfo("500","商品号不能为空!");
        }
        return managerService.deletePointProductInfoByProductNumber(productNumber);
    }

    //管理订单信息
    //1. 显示订单信息
    @GetMapping("/GetAllOrderInfos")
    public ReturnInfo getAllOrderInfo()
    {
        return managerService.getAllOrderInfo();
    }
    //2. 根据订单号得到订单信息//订单号+价格+是否发货+是否收货
    @PostMapping("/GetPointOrder")
    public ReturnInfo getPointOrderInfo(String orderNumber)
    {
        if (orderNumber==null||orderNumber.length()==0)
        {
            return new ReturnInfo("500","订单号不能为空！");
        }
        return managerService.getPointOrderInfo(orderNumber);
    }
}
