package com.tumiao.service.serviceInterface.userOperation;

import com.tumiao.utils.ReturnInfo;

import java.util.List;

//购物车操作服务
public interface ShoppingCartsService {

    // 1. 显示购物车商品的所有信息: 在购物车表里查询对应用户名的所有商品信息
    public ReturnInfo getAllShoppingCartsInfo(String username);

    // 2. 将选中商品移除购物车:用户名和商品号列表
    public ReturnInfo deletePointProductFromShoppingCarts(String username, List<String> productNumbers);

    // 3. 将选中商品进行购买结算：返回是否结算成功，并将购买的商品生成相应订单
    public  ReturnInfo purchaseProducts(String username,List<String> productNumbers);//购买的用户名+需要购买的商品号列表
}
