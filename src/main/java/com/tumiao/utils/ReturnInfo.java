package com.tumiao.utils;

import lombok.Data;

@Data
public class ReturnInfo {
    private Object data;//数据
    private String msg;//提示信息
    private String code;
    public ReturnInfo(Object data,String code,String msg)
    {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
    public ReturnInfo(String code,String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
