package com.tumiao.utils;

import java.util.Random;

//创建唯一编号
public class CreateOneNumberUtils
{
    //创建唯一的订单号
    //高并发下，为确保用户的唯一性，可以把用户id加进去
    //用时间戳来创建唯一的订单号（但在高并发，时间极短的情况下仍有重复的可能性，可以将时间换为纳米级别，或加上用户名或数据库自增长id或加上随机字符串）
    public static String getOrderNumber()
    {
        String order = "order_";
        long time = System.currentTimeMillis();
        order += time;
        Random random = new Random();
        for(int i = 0;i<6;i++)
        {
            order += (char)('A'+random.nextInt(26));
        }
        return order;
    }

    //创建唯一商品号
    public static String getProductNumber()
    {
        String product = "product_";
        long time = System.currentTimeMillis();
        product += time;
        Random random = new Random();
        for(int i = 0;i<6;i++)
        {
            product += (char)('A'+random.nextInt(26));
        }
        return product;
    }

    //创建图片名
    public static String getImageName()
    {
        String image = "img_";
        long time = System.currentTimeMillis();
        image+=time;
        Random random = new Random();
        for(int i = 0;i<6;i++)
        {
            image += (char)(random.nextInt(26)+'A');
        }
        return image;
    }
}
