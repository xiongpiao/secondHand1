package com.tumiao.dao;

import com.tumiao.entity.Orders;
import com.tumiao.entity.Product_info;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderDao {
    @Select("SELECT * FROM orders WHERE user_name = #{username}")
    List<Orders> getOrders(String username);
    @Select("SELECT * FROM product_info WHERE product_number = #{product_number}")
    Product_info getProduct_info(String product_number);
    @Update("UPDATE orders SET is_get = \"1\" WHERE user_name = #{username} AND product_number = #{product_number}")
    void isGetProduct(String username, String product_number);
    @Select("SELECT product_number FROM product_info WHERE user_name = #{username} AND be_purchased = \"1\"")
    List<String> getProductNumbers(String username);
    @Select("SELECT * FROM orders WHERE product_number = #{productNumber}")
    Orders getOrdersByProductNumber(String productNumber);
    @Update("UPDATE orders SET is_put = \"1\" WHERE order_number = #{orderNumber}")
    void isPutProduct(String orderNumber);
    @Select("SELECT * FROM product_info WHERE user_name = #{username}")
    List<Product_info> getAllPutInfos(String username);


    @Delete("DELETE FROM product_info WHERE product_number = #{product_number} and user_name = #{username} AND be_purchased = \"0\"")
    void deleteMyPointProduct(String username, String product_number);
    @Select("SELECT be_purchased FROM product_info WHERE product_number = #{product_number}")
    String getIsPurchased(String product_number);
    @Delete("DELETE FROM put_shoppingcarts WHERE product_number = #{product_number}")
    void deleteProductShoppingCarts(String product_number);
}
