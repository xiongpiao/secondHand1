package com.tumiao.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShowOnSaleProduct {
    private String title;//标题
    private Integer price;//价格
    private String product_number;//商品号
    private String school_name;//学校名称
    private String product_type_name;//产品类型
    private String user_name;//用户名
    private String area_name;//地区名
    private MultipartFile image;//产品图片地址
}
