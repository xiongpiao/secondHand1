package com.tumiao.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OnSaleProduct {
    Integer price;//价格
    String title;//标题
    MultipartFile multipartFile;//图片
    String description;//描述
    String type;//类别
    String area;//地区
    String school;//学校
}
