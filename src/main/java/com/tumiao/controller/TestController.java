package com.tumiao.controller;

import com.tumiao.dao.TestDao;
import com.tumiao.pojo.Test;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Test")
public class TestController {

//    @PostMapping("/PutImage")
//    public String putImage(HttpServletRequest request,@RequestParam MultipartFile file)
//    {
//       return upload(file,request);
//    }
@PostMapping("/PutImage")
public String putImage(HttpServletRequest request, @ModelAttribute @Validated Test test)
{
    String price = test.getPrice();
    return upload(test.getFile(),request);
}
    //绑定文件上传路径到uploadPath
    @Value("${web.upload-path}")
    private String uploadPath;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    //进行文件上传,返回文件上传地址
    public String upload(MultipartFile uploadFile
            , HttpServletRequest request)
    {
        //在uploadPath文件夹中通过日期对上传的文件进行归类保存,根据日期创建对应的文件夹
        String format = sdf.format(new Date());
        File folder = new File(uploadPath+format);
        if(!folder.isDirectory())//如果文件不存在或者该文件不是文件夹
        {
            folder.mkdirs();//对文件夹进行创建//注意这里是mkdirs()，不是mkdir()因为这里创建了多个文件目录
        }
        //对上传的文件重命名
        String oldName = uploadFile.getOriginalFilename();//得到原始文件名
        String newName = UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //文件保存
            uploadFile.transferTo(new File(folder,newName));
            //返回上传文件的访问路径
            String filePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + "/" + format + newName;
            return filePath;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw  new RuntimeException("文件保存失败!");
        }
    }
    @Autowired
    TestDao testDao;
    @GetMapping("/Usernames")
    public ReturnInfo getUsernames()
    {
        List<String> usernames = testDao.getUsernames();
        return new ReturnInfo(usernames,"200","数据请求成功！");
    }
}
