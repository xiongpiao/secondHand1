package com.tumiao.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("SELECT user_name FROM USER WHERE user_name = #{username} AND user_password = #{password}")
    String getUsername( String username,  String password);
    @Select("SELECT user_name FROM USER WHERE user_name = #{username}")
    String selectUsername(String username);
    @Insert("INSERT INTO USER VALUES(#{username},#{password});")
    void registerUser(String username, String password);
}
