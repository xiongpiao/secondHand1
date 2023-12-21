package com.tumiao.entity;

import lombok.Data;

@Data
public class Orders {
    private String order_number;
    private String user_name;
    private String product_number;
    private String is_put;
    private String is_get;
}
