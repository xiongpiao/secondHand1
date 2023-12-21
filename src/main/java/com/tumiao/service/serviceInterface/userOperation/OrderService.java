package com.tumiao.service.serviceInterface.userOperation;

import com.tumiao.utils.ReturnInfo;

//订单管理
public interface OrderService {
    //  购买的订单管理
        //1. 查询自身购买的所有订单：根据用户名从订单列表中查询
        //返回订单信息+订单状态+是否确认收货的状态
        public ReturnInfo getAllPurchaseOrders(String username);

        //2. 对订单进行确认收货
        public ReturnInfo isGetProduct(String username,String product_number);

    // 客户订单管理
        //1. 查询所有客户购买的订单：
        //返回订单信息+订单状态（是否发货）
        public ReturnInfo getAllCustomerOrders(String username);

        //2. 对订单进行发货操作
        public ReturnInfo isPutProduct(String username,String orderNumber);//可以进行校验等操作
    // 自身发布的所有商品
        //查询自身发布的所有商品
        public ReturnInfo getAllPutInfos(String username);

        //下架自身的商品：注意：该商品没有被购买过！
        public ReturnInfo deleteMyPointProduct(String username,String product_number);
}
