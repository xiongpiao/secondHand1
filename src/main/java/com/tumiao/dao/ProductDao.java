package com.tumiao.dao;

import com.tumiao.entity.Product_info;
import com.tumiao.pojo.ProductDemo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductDao {
//    @Select("SELECT product_image,title,price,product_number FROM product_info WHERE title LIKE CONCAT('%',#{info},'%' ) AND be_purchased != '1'")
//    List<ProductDemo> getProductDemo(String info);
    @Select("SELECT * FROM product_info WHERE product_number = #{productNumber}")
    Product_info getAccProduct(String productNumber);
    @Insert("INSERT INTO put_shoppingcarts VALUES(#{username},#{productNumber})")
    void putProductToShoppingCarts(String username, String productNumber);
    @Insert("INSERT INTO orders VALUES(#{orderNumber},#{username},#{productNumber},\"0\",\"0\")")
    boolean purchaseProduct(String orderNumber, String username, String productNumber);
    @Select("SELECT be_purchased FROM product_info WHERE product_number = #{productNumber}")
    String isPurchased(String productNumber);
    @Update("UPDATE product_info SET be_purchased = \"1\" WHERE product_number = #{productNumber}")
    void setProductIsPurchased(String productNumber);
    @Insert("INSERT INTO product_info VALUES(#{title},#{price},#{description}" +
            ",#{productNumber},#{username},#{path},#{be_purchased}" +
            ",#{area},#{school},#{type})")
    void onSaleProduct(String title
            , Integer price, String description
            , String productNumber, String school
            , String type, String username
            , String area, String path
            , String be_purchased);
    @Select("SELECT * FROM product_info WHERE be_purchased = \"0\"")
    List<Product_info> AllProductInfo();
    @Select("SELECT * FROM product_info WHERE title LIKE CONCAT('%',#{info},'%' ) AND be_purchased != '1'")
    List<Product_info> getProductInfo(String info);
}
