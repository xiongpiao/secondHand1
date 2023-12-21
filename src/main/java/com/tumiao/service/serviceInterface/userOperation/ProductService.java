package com.tumiao.service.serviceInterface.userOperation;

import com.tumiao.entity.Product_info;
import com.tumiao.pojo.OnSaleProduct;
import com.tumiao.utils.ReturnInfo;

import javax.servlet.http.HttpServletRequest;

//商品操作
public interface ProductService {
    //1. 首页对商品进行模糊查询
    public ReturnInfo getInfoByInfo(String info);

    //2. 点击商品对商品进行详细查看：返回指定商品的详细信息
    public ReturnInfo getAllInfoByProductNumber(String productNumber);

    //3. 将商品加入购物车
    public ReturnInfo putProductToShoppingCarts(String username,String productNumber);

    //4. 购买商品:存到订单列表中
    public ReturnInfo purchaseProduct(String username,String productNumber);

    //5. 上架商品:价格+标题+图片+描述+类别+地区+学校
    public ReturnInfo onSaleProduct(HttpServletRequest request,OnSaleProduct onSaleProduct);

    //6. 得到发布商品的所有信息//首页页面信息展示
    public ReturnInfo getAllOnSaleProducts();
}
