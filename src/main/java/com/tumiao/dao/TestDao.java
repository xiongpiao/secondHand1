package com.tumiao.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestDao {
    @Select("select user_name from user")
    public List<String> getUsernames();
}
