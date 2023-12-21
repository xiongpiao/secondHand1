package com.tumiao.service.serviceImpl.userOperationImpl;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.tumiao.dao.OrderDao;
import com.tumiao.entity.Orders;
import com.tumiao.entity.Product_info;
import com.tumiao.pojo.Order_info;
import com.tumiao.service.serviceInterface.userOperation.OrderService;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    @Override
    //1. 查询自身购买的所有订单：根据用户名从订单列表中查询
    //返回订单信息+是否发货+是否确认收货的状态
    public ReturnInfo getAllPurchaseOrders(String username) {
        List<Order_info> order_infos = new ArrayList<>();
       try {
           //先根据用户名查询到自身购买商品的订单简要信息
           List<Orders> orders = orderDao.getOrders(username);
           for(int i = 0;i<orders.size();i++)
           {
               Orders order = orders.get(i);
               String product_number = order.getProduct_number();
               if(product_number == null || product_number.length()==0)
                   throw new RuntimeException("商品号不能为空!");

               //查询到订单详细信息
               Product_info product_info = orderDao.getProduct_info(product_number);
               //是否发货
               String is_put = order.getIs_put();
               //是否确认收货
               String is_get = order.getIs_get();

               //订单信息
               Order_info order_info = new Order_info();
               order_info.setProduct_info(product_info);
               order_info.setIs_put(is_put);
               order_info.setIs_get(is_get);
               order_info.setOrder_number(order.getOrder_number());
               order_infos.add(order_info);
           }
           return new ReturnInfo(order_infos,"200","订单查询成功！");
       }
       catch (Exception e)
       {
           return new ReturnInfo("500","查询失败！");
       }
    }

    @Override
    //对自身购买的订单进行确认收货
    public ReturnInfo isGetProduct(String username, String product_number) {
        try {
            orderDao.isGetProduct(username,product_number);
            return new ReturnInfo("200","确认收货成功！");
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ReturnInfo("500","确认收货失败！");
        }
    }

    //查看用户卖出的订单信息
    @Override
    public ReturnInfo getAllCustomerOrders(String username) {
        List<Order_info> order_infos = new ArrayList<>();
        try {
            //根据用户名查询到已经被购买的商品的商品号列表
            List<String> productNumbers = orderDao.getProductNumbers(username);
            for(int i = 0;i<productNumbers.size();i++)
            {
                Order_info order_info = new Order_info();
                String productNumber = productNumbers.get(i);
                //根据商品号得到商品信息
                Product_info product_info = orderDao.getProduct_info(productNumber);
                order_info.setProduct_info(product_info);
                //根据商品号得到订单的简要信息
                Orders order = orderDao.getOrdersByProductNumber(productNumber);
                if(order == null)//如果查询到的订单为空
                    throw new RuntimeException();
                order_info.setIs_put(order.getIs_put());
                order_info.setIs_get(order.getIs_get());
                order_info.setOrder_number(order.getOrder_number());
                order_infos.add(order_info);
            }
            return new ReturnInfo(order_infos,"200","订单查询成功！");
        }
        catch (Exception e)
        {
            return new ReturnInfo("500","查询失败！");
        }
    }

    @Override//对用户购买的订单进行发货
    public ReturnInfo isPutProduct(String username, String orderNumber) {
        try {
            orderDao.isPutProduct(orderNumber);
            return new ReturnInfo("200","发货成功！");
        }
        catch (Exception e)
        {
            return new ReturnInfo("500","发货失败！");
        }
    }

    @Override//查询自身发布的所有商品
    public ReturnInfo getAllPutInfos(String username) {
        try {
            List<Product_info> product_infos = orderDao.getAllPutInfos(username);
            return new ReturnInfo(product_infos,"200","查询成功");
        }
        catch (Exception e)
        {
            return new ReturnInfo("500","查询失败！");
        }
    }

    @Override//对自身发布的商品进行下架操作，注意该商品没有被购买过
    public ReturnInfo deleteMyPointProduct(String username,String product_number) {
        try {
            String flag = orderDao.getIsPurchased(product_number);//得到商品是否已经被购买
            if(flag.equals("1"))
                return new ReturnInfo("500","该商品已经被购买，不能进行删除！");
            orderDao.deleteProductShoppingCarts(product_number);//删除指定商品在购物车中的信息
            orderDao.deleteMyPointProduct(username,product_number);//删除指定商品信息
            return new ReturnInfo("200","下架商品成功！");
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ReturnInfo("500","下架商品失败！");
        }
    }
}
