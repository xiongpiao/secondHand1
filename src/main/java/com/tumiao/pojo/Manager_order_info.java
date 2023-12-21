package com.tumiao.pojo;

import lombok.Data;

@Data
public class Manager_order_info {
    //订单号+价格+是否发货+是否收货
    private String order_number;
    private Integer price;
    private String is_put;//是否发货
    private String is_get;//是否收货
}
