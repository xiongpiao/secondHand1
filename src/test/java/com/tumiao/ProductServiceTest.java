package com.tumiao;

import com.tumiao.dao.ProductDao;
import com.tumiao.entity.Product_info;
import com.tumiao.pojo.OnSaleProduct;
import com.tumiao.pojo.ProductDemo;
import com.tumiao.service.serviceInterface.userOperation.ProductService;
import com.tumiao.utils.CreateOneNumberUtils;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    ProductService productService;
    @Autowired
    ProductDao productDao;
    @Test
    public void test1()
    {
        ReturnInfo o = productService.getInfoByInfo("oaghasskjg");
        List<ProductDemo> list = (List<ProductDemo>) o.getData();
        System.out.println(list);
    }
    @Test
    public void test2()
    {
        List<Product_info> slghsdkl = productDao.getProductInfo("111");
        if(slghsdkl.size()==0)
            System.out.println("null");
        System.out.println(slghsdkl);
    }
    @Test
    public void test3()
    {
        Product_info accProduct = productDao.getAccProduct("222");
        System.out.println(accProduct);
    }
    @Test
    public void test4()
    {
        productDao.putProductToShoppingCarts("111","sdogishd");
    }
    @Test
    public void test5()
    {
        ReturnInfo slghs = productService.putProductToShoppingCarts("abeyam", "004a21ed9a5e564decfdb33053a67878");
        System.out.println(slghs.getMsg());
    }
    @Test
    public void test6()
    {
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void test7()
    {
        boolean abem = productDao
                .purchaseProduct("2356923652"
                        , "abem"
                        , "0063121c75dd94d3c8082f9200c3fd2b");
        System.out.println(abem);
    }

    @Test
    public void test8()
    {
        boolean abem = productDao
                .purchaseProduct("235692365225"
                        , "abe2363652m"
                        , "23523623623");
        System.out.println(abem);
    }

    @Test
    public void test9()
    {
        productDao.setProductIsPurchased("sdghisdhghj111111");
    }

    @Test
    public void test10()
    {
        String shghoisdhgjks = productDao.isPurchased("shghoisdhgjks");
        System.out.println(shghoisdhgjks);
    }

    //事务测试
    @Test
//    @Transactional
    public void test11()
    {
        productDao.setProductIsPurchased("01669a6cad2388e016795853f4c152a9");

//        try {
//            productDao.setProductIsPurchased("01658829f43fee067b69ba7b7fa8d3a2");
////            productDao.purchaseProduct("soguiosedyg", "soidighilsd", "lhgklsdhglsh");
//        }
//        catch (Exception e)
//        {
//            System.out.println("sdhglsdhglsdh");
//        }
    }
    @Test
    public void test12()
    {
        productService.purchaseProduct("ty323572","w11111111");
    }
    @Test
    public void test13()
    {
        productDao.onSaleProduct("title"
                ,200,"description"
                ,"product_number","重庆理工大学"
                ,"美妆","saiwc"
                ,"北京","D://","0");
    }
    @Test
    public void test14()
    {
        productDao.onSaleProduct("title"
                ,200,"description"
                ,"product_number","重庆理工大学"
                ,"美妆","zql"
                ,"北京","D://","0");
    }

    @Test
    public void test15()
    {
        try {
            throw new IOException();
        }
        catch (IOException e)
        {
            System.out.println("第一个");
        }
        catch (RuntimeException e)
        {
            System.out.println("第二个");
        }
    }
    @Test
    public void test16()
    {
            File file = new File("D:\\second_images");
            if (file.exists())
            {
                System.out.println("该文件目录已存在");
            }
            else
            {
                if (file.mkdir())
                {
                    System.out.println("文件创建成功");
                }
                else
                {
                    System.out.println("文件创建失败");
                }
            }
//        System.out.println(path);
    }
    @Test
    //上架商品：将商品放入商品信息列表
    public void onSaleProduct()
    {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get("C:\\Users\\tu'miao\\Pictures\\近期.jpg"));
            //2. 图片名称
            String image_name = CreateOneNumberUtils.getImageName();
            //3. 得到图片文件路径
            String path = createFile() + "\\" +image_name+".jpg";
            //5. 将图片存储到指定文件中
//            MultipartFile image = onSaleProduct.getMultipartFile();
//            byte[] bytes = image.getBytes();
            Path path0 = Paths.get(path);
            Files.write(path0,imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String createFile() {
        String path = "D:\\secondhand_images";
        File file = new File(path);
        if (file.exists())
        {
            return path;
        }
        else
        {
            if (file.mkdir())
            {
                System.out.println("文件创建成功");
                return path;
            }
            else
            {
                System.out.println("文件创建失败");
                throw new RuntimeException("文件创建失败");
            }
        }
    }
}
