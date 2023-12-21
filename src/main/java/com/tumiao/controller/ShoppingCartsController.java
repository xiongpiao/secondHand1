package com.tumiao.controller;

import com.tumiao.pojo.Product_number;
import com.tumiao.service.serviceInterface.userOperation.ShoppingCartsService;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ShoppingCartController")
public class ShoppingCartsController {
    @Autowired
    ShoppingCartsService shoppingCartsService;

    @GetMapping("/Product_info")
    public ReturnInfo getProductInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null) {
            Map<String, String> parse =
                    JwtUtils.parse(token);
            String username = parse.get("username");
            return shoppingCartsService.getAllShoppingCartsInfo(username);
        }
        return new ReturnInfo("500", "您还未登录");
    }

    @PostMapping("/DeletePointProduct")
    public ReturnInfo deletePointProductFromShoppingCarts(HttpServletRequest request
            , @RequestBody Product_number product_number) {
        if (product_number == null)
            return new ReturnInfo("500","商品号不能为空！");
        List<String> product_numbers = product_number.getProduct_numbers();
        if (product_numbers == null || product_numbers.size() == 0)
            return new ReturnInfo("500", "商品号不能为空！");
        String token = request.getHeader("token");
        if (token == null)
            return new ReturnInfo("500", "您还未登录");
        try {
            Map<String, String> parse = JwtUtils.parse(token);
            String username = parse.get("username");
            return shoppingCartsService.deletePointProductFromShoppingCarts(username, product_numbers);
        } catch (Exception e) {
            return new ReturnInfo("500", "商品删除失败！");
        }
    }

    @PostMapping("/PurchaseProducts")//购买购物车中的商品//将购物车中商品删除，并将商品并生成订单
    public ReturnInfo purchaseProducts(HttpServletRequest request, @RequestBody Product_number product_number)
    {
        String token = request.getHeader("token");
        if(token==null)
        {
            return new ReturnInfo("500","用户未登录！");
        }
        if(product_number == null )
        {
            return  new ReturnInfo("500","商品号不能为空！");
        }
        List<String> product_numbers = product_number.getProduct_numbers();
        if(product_numbers==null||product_numbers.size()==0)
        {
            return new ReturnInfo("500","商品号不能为空！");
        }
        try {
            Map<String, String> parse = JwtUtils.parse(token);
            String username = parse.get("username");
            return shoppingCartsService.purchaseProducts(username,product_numbers);
        }catch (Exception e)
        {
            return new ReturnInfo("500","用户登录信息错误！");
        }
    }
}
