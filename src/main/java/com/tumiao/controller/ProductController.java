package com.tumiao.controller;

import com.tumiao.entity.Product_info;
import com.tumiao.pojo.OnSaleProduct;
import com.tumiao.service.serviceInterface.userOperation.ProductService;
import com.tumiao.utils.JwtUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/ProductController")
public class ProductController {
    @Autowired
    ProductService productService;

    // 1.对商品进行模糊查询，并展示其大致信息
    @PostMapping("/GetInfoByInfo")
    public ReturnInfo getInfoByInfo(@RequestParam String info)
    {
        if(info==null||info.length()==0)
        {
            return new ReturnInfo("500","输入信息不能为空！");
        }
       return productService.getInfoByInfo(info);
    }

    //2. 点击商品对商品进行详细查看：返回指定商品的详细信息
    @PostMapping("/GetAccInfo")
    public ReturnInfo getAccInfo(@RequestParam String productNumber)//根据商品号，返回指定商品的详细信息
    {
        if(productNumber==null||productNumber.length()==0)
            return new ReturnInfo("500","订单号不能为空！");
        return productService.getAllInfoByProductNumber(productNumber);
    }

    //3. 将商品加入购物车//需要商品号，用户名，用户名可以通过请求头得到
    @PostMapping("/PutProductIntoShoppingCarts")
    public ReturnInfo putProductToShoppingCarts(HttpServletRequest request, @RequestParam String productNumber)
    {
        if(productNumber==null||productNumber.length()==0)
        {
            return new ReturnInfo("500","商品号输入不能为空！");
        }
        String token = request.getHeader("token");
        Map<String, String> info = JwtUtils.parse(token);
        String username = info.get("username");
        return productService.putProductToShoppingCarts(username,productNumber);
    }

    //4. 购买商品:存到订单列表中//需要用户名和商品号，以及自己生成唯一的订单号
    @PostMapping("/PurchaseProduct")
    public ReturnInfo purchaseProduct(HttpServletRequest request,@RequestParam String productNumber)
    {
        if(productNumber==null||productNumber.length()==0)
        {
            return new ReturnInfo("500","商品号输入不能为空!");
        }
        String token = request.getHeader("token");
        Map<String, String> parse = JwtUtils.parse(token);
        String username = parse.get("username");
        return productService.purchaseProduct(username,productNumber);
    }

    //5. 上架商品:价格+标题+图片+描述+类别+地区+学校
    @PostMapping("/OnSaleProduct")
    public ReturnInfo onSaleProduct(HttpServletRequest request,@ModelAttribute @Validated OnSaleProduct onSaleProduct)
    {
        if (onSaleProduct.getTitle()==null||onSaleProduct.getTitle().length()==0)
        {
            return new ReturnInfo("500","输入标题不能为空");
        }
        if (onSaleProduct.getMultipartFile()==null)
        {
            return new ReturnInfo("500","输入图片不能为空");
        }
        if (onSaleProduct.getDescription()==null||onSaleProduct.getDescription().length()==0)
        {
            return new ReturnInfo("500","商品描述内容不能为空");
        }
        if(onSaleProduct.getType()==null||onSaleProduct.getType().length()==0)
        {
            return new ReturnInfo("500","商品类别不能为空");
        }
        if(onSaleProduct.getArea()==null||onSaleProduct.getArea().length()==0)
        {
            return new ReturnInfo("500","地区输入不能为空");
        }
        if (onSaleProduct.getSchool()==null||onSaleProduct.getSchool().length()==0)
        {
            return new ReturnInfo("500","学校输入不能为空");
        }
        return productService.onSaleProduct(request,onSaleProduct);
    }

    //6. 首页页面信息

    @GetMapping("/GetAllOnSaleProduct")
    public ReturnInfo getAllOnSaleProduct()
    {
        return productService.getAllOnSaleProducts();
    }

}
