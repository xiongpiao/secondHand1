package com.tumiao.service.serviceInterface.ManagerOperation;

import com.tumiao.utils.ReturnInfo;
import javax.servlet.http.HttpServletResponse;


//管理员的可进行的操作
public interface ManagerService {
    //登录
    //管理员登录
       public ReturnInfo managerLogin(HttpServletResponse response, String manager_name, String password);

    //管理用户信息
        //1. 查看所有用户信息
        public ReturnInfo getAllUserInfo();
        //2. 修改指定用户密码
        public ReturnInfo updatePointUserInfoByUserName(String username,String new_passqord);
        //3. 根据用户名搜索用户信息
        public ReturnInfo getUserInfo(String username);
   //管理商品信息
        //1. 查看所有未被购买的商品信息
        public ReturnInfo getAllProductInfoNotBePurchased();
        //2. 删除指定商品信息
        public ReturnInfo deletePointProductInfoByProductNumber(String productNumber);

   //管理订单信息
        //1. 显示订单信息////订单号+价格+是否发货+是否收货
        public ReturnInfo getAllOrderInfo();
        //2. 根据订单号得到订单信息//订单号+价格+是否发货+是否收货
        public ReturnInfo getPointOrderInfo(String orderNumber);
}
