package com.tumiao.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Test {
    MultipartFile file;
    String price;
}
