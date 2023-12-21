package com.tumiao.pojo;

import com.tumiao.entity.Product_info;
import lombok.Data;

import java.util.List;

@Data
public class Order_info {
    private String order_number;//订单
    private Product_info product_info;//订单信息
    private String is_put;//是否发货
    private String is_get;//是否收货
}
