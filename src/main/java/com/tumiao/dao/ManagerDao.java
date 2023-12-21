package com.tumiao.dao;

import com.tumiao.entity.Orders;
import com.tumiao.entity.Product_info;
import com.tumiao.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ManagerDao {
    @Select("SELECT manager_name FROM manager WHERE manager_name = #{manager_name} AND manager_password = #{password}")
    String getName(String manager_name, String password);
    @Select("SELECT * FROM USER")
    List<User> gerUserInfos();
    @Select("SELECT * FROM product_info")
    List<Product_info> getProductInfos();
    @Update("UPDATE USER SET user_password = #{new_password} WHERE user_name = #{username}")
    void updatePointUserInfo(String username, String new_password);
    @Select("SELECT * FROM USER WHERE user_name = #{username}")
    User gerUserInfo(String username);
    @Select("SELECT * FROM  product_info WHERE be_purchased = \"0\"")
    List<Product_info> getProductInfosNotBePurchased();
    @Delete("DELETE FROM product_info WHERE product_number = #{productNumber} AND be_purchased = \"0\"")
    void deletePoint(String productNumber);
    @Select("SELECT be_purchased FROM product_info WHERE product_number = #{productNumber} AND be_purchased = \"0\"")
    String getBePurchased(String productNumber);
    @Select("SELECT * FROM orders")
    List<Orders> gerOrders();
    @Select("SELECT price FROM product_info WHERE product_number = #{product_number}")
    Integer getPrice(String product_number);
    @Select("SELECT * FROM orders WHERE order_number = #{orderNumber}")
    Orders getOrder(String orderNumber);
}
