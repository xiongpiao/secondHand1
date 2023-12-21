package com.tumiao.dao;

import com.tumiao.entity.Product_info;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartDao {
    @Select("SELECT * FROM product_info \n" +
            "WHERE product_number IN \n" +
            "(SELECT product_number FROM put_shoppingcarts WHERE user_name = #{username})")
    List<Product_info> getAllShoppingCartsInfo(String username);
    @Delete("DELETE FROM put_shoppingcarts WHERE user_name = #{username} AND product_number = #{product_number}")
    void deleteProduct(String username, String product_number);
    @Insert("INSERT INTO orders VALUES(#{order_number},#{username},#{productNumber},\"0\",\"0\")")
    void createOrder(String order_number, String username, String productNumber);
    @Update("UPDATE product_info SET be_purchased = \"1\" WHERE product_number = #{productNumber}")
    void is_purchased(String productNumber);
    @Select("SELECT be_purchased FROM product_info WHERE product_number = #{productNumber}")
    String getIs_purchased(String productNumber);
}
