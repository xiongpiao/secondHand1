package com.tumiao.controller;

import com.tumiao.service.serviceInterface.userOperation.OrderService;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    @Autowired
    OrderService orderService;

    //  购买的订单管理
    //1. 查询自身购买的所有订单：根据用户名从订单列表中查询
    //返回订单信息+是否发货+是否确认收货的状态
    @GetMapping("/GetAllOrderInfo")
    public ReturnInfo getAllPurchaseOrders(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        if(token==null||token.length()==0)//这些操作十分频繁，可以通过过滤器来进行操作
        {
            return new ReturnInfo("500","用户未登录");
        }
        Map<String, String> parse = JwtUtils.parse(token);
        String username = parse.get("username");
        return orderService.getAllPurchaseOrders(username);
    }

    //2. 对自身购买的订单进行确认收货
    @PostMapping("/IsGetProduct")
    public ReturnInfo isGetProduct(@RequestParam String product_number,HttpServletRequest request)
    {
        if(product_number==null||product_number.length()==0)
            return new ReturnInfo("500","订单号不能为空！");
        String token = request.getHeader("token");
        Map<String, String> parse = JwtUtils.parse(token);
        String username = parse.get("username");
        return orderService.isGetProduct(username,product_number);
    }
//
//    // 客户订单管理
//    //1. 查询所有客户购买的订单：
//    //返回订单信息+订单状态（是否发货）
    @GetMapping("/GetAllCustomerOrders")
    public ReturnInfo getAllCustomerOrders(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        if(token==null||token.length()==0)//这些操作十分频繁，可以通过过滤器来进行操作
        {
            return new ReturnInfo("500","用户未登录");
        }
        Map<String, String> parse = JwtUtils.parse(token);
        String username = parse.get("username");
        return orderService.getAllCustomerOrders(username);
    }

    //2. 对订单进行发货操作
    @PostMapping("/IsPutProduct")
    public ReturnInfo isPutProduct(HttpServletRequest request,@RequestParam String orderNumber)//可以进行校验等操作
    {
        if(orderNumber==null||orderNumber.length()==0)
        {
            return new ReturnInfo("500","订单号不能为空！");
        }
        String token = request.getHeader("token");
        Map<String, String> parse = JwtUtils.parse(token);
        String username = parse.get("username");
        return orderService.isPutProduct(username,orderNumber);
    }

    //查询自身发布的所有商品
    @GetMapping("/GetAllPutInfos")
    public ReturnInfo getAllPutInfos(HttpServletRequest request)
    {
        try {
            String token = request.getHeader("token");
            if(token == null)
                throw new RuntimeException("用户未登录");
            Map<String, String> parse = JwtUtils.parse(token);
            String username = parse.get("username");
            return orderService.getAllPutInfos(username);
        }catch (Exception e)
        {
            return new ReturnInfo("500","用户未登录！");
        }
    }
    //下架自身的商品：注意：该商品没有被购买过！
    @PostMapping("/DeleteMyPointProduct")
    public ReturnInfo deleteMyPointProduct(HttpServletRequest request,@RequestParam String product_number)
    {
        if(product_number == null || product_number.length()==0)
        {
            return new ReturnInfo("500","商品号不能为空！");
        }
        try {
            String token = request.getHeader("token");
            if(token == null)
                throw new NullPointerException();
            Map<String, String> parse = JwtUtils.parse(token);
            String username = parse.get("username");
            return orderService.deleteMyPointProduct(username,product_number);
        }catch (Exception e)
        {
            return new ReturnInfo("500","用户未登录!");
        }
    }
}
