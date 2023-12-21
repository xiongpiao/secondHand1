package com.tumiao.service.serviceImpl.userOperationImpl;

import com.tumiao.dao.ProductDao;
import com.tumiao.entity.Product_info;
import com.tumiao.pojo.OnSaleProduct;
import com.tumiao.pojo.ProductDemo;
import com.tumiao.pojo.ShowOnSaleProduct;
import com.tumiao.service.serviceInterface.userOperation.ProductService;
import com.tumiao.utils.CreateOneNumberUtils;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    //模糊查询，根据标题进行模糊查询，返回：商品信息数组，
    public ReturnInfo getInfoByInfo(String info) {
        List<Product_info> productDemos = productDao.getProductInfo(info);
        if(productDemos.size()!=0)
            return new ReturnInfo(productDemos,"200","查询成功！");
        else
            return new ReturnInfo("500","抱歉，未查询到相关数据！");
    }

    @Override
    public ReturnInfo getAllInfoByProductNumber(String productNumber) {
        Product_info product_info = productDao.getAccProduct(productNumber);
        if(product_info==null)
        {
            return new ReturnInfo("500","该商品号不存在");
        }
        else
        {
            return new ReturnInfo(product_info,"200","商品信息查询成功");
        }
    }

    @Override//将商品加入购物车
    public ReturnInfo putProductToShoppingCarts(String username, String productNumber) {
        try {
            productDao.putProductToShoppingCarts(username, productNumber);
        }
        catch (Exception e)
        {
            return new ReturnInfo("500","加入购物车失败!您已将该商品加入过购物车！或者 用户名或商品号不存在！");
        }
        return new ReturnInfo("200","加入购物车成功！");
    }

    @Override
    public ReturnInfo purchaseProduct(String username, String productNumber) {
        String orderNumber = CreateOneNumberUtils.getOrderNumber();

        try {
            String flag = productDao.isPurchased(productNumber);
            if(flag==null||flag.equals("1"))
                return new ReturnInfo("500","该商品不存在或已被购买！");
            productDao.purchaseProduct(orderNumber, username, productNumber);
            productDao.setProductIsPurchased(productNumber);
            return new ReturnInfo("200","商品购买成功！");
        }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ReturnInfo("500","该商品已被购买或不存在！");
        }

    }

    @Override
    //上架商品：将商品放入商品信息列表
    public ReturnInfo onSaleProduct(HttpServletRequest request,OnSaleProduct onSaleProduct)
    {
        try {
            //1. 得到用户名
            String token = request.getHeader("token");
            Map<String, String> parse = JwtUtils.parse(token);
            String username = parse.get("username");
            //2. 将图片存储到指定文件中
            MultipartFile image = onSaleProduct.getMultipartFile();
            String path = upload(image, request);//将文件保存并将文件路径返回
            productDao.onSaleProduct(onSaleProduct.getTitle()
                    ,onSaleProduct.getPrice()
                    ,onSaleProduct.getDescription()
                    ,CreateOneNumberUtils.getProductNumber()
                    ,onSaleProduct.getSchool()
                    ,onSaleProduct.getType()
                    ,username
                    ,onSaleProduct.getArea()
                    ,path
                    ,"0");
        }
        catch (Exception e )
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ReturnInfo("500","商品上架失败!");
        }
        return new ReturnInfo("200","商品上架成功");
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
    @Override
    //首页页面信息展示
    public ReturnInfo getAllOnSaleProducts() {
        List<Product_info> product_infos = productDao.AllProductInfo();
        return new ReturnInfo(product_infos,"200","首页信息展示成功！");
    }
}
