package com.tumiao.Minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//lombok自动get,set
//文件地址返回路径的实体类
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private String minIoUrl;
}
