package com.tumiao.service.serviceImpl.userOperationImpl;

import com.tumiao.dao.ShoppingCartDao;
import com.tumiao.entity.Product_info;
import com.tumiao.service.serviceInterface.userOperation.ShoppingCartsService;
import com.tumiao.utils.CreateOneNumberUtils;
import com.tumiao.utils.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
@Service
//@Transactional
public class ShoppingCartsServiceImpl implements ShoppingCartsService
{
    @Autowired
    ShoppingCartDao shoppingCartDao;
    @Override
    // 1. 显示购物车商品的所有信息: 在购物车表里查询对应用户名的所有商品信息
    public ReturnInfo getAllShoppingCartsInfo(String username) {
        List<Product_info> product_infos = shoppingCartDao.getAllShoppingCartsInfo(username);
        return new ReturnInfo(product_infos,"200","商品查询成功!");
    }

    @Override
    // 2. 将选中商品移除购物车:用户名和商品号列表
    public ReturnInfo deletePointProductFromShoppingCarts(String username, List<String> productNumbers) {
        try {
            for(int i = 0;i<productNumbers.size();i++)
            {
                String product_number = productNumbers.get(i);
                if(product_number == null || product_number.length() == 0)
                {
                    throw new RuntimeException("商品号不能为空！");
                }
                    shoppingCartDao.deleteProduct(username, product_number);
                }
            }
        catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
            return new ReturnInfo("500","商品删除失败！请检查是否有商品号为空！");
        }
        return new ReturnInfo("200","商品删除成功");
    }

    @Override
    // 3. 将选中商品进行购买结算: 并将购买的商品生成相应订单,并删除对应商品购物车中的信息返回是否结算成功
    @Transactional
    public ReturnInfo purchaseProducts(String username, List<String> productNumbers) {
        try {
            for(int i = 0;i<productNumbers.size();i++)
            {
                String productNumber = productNumbers.get(i);
                if(productNumber==null||productNumber.length()==0)
                    throw new RuntimeException("商品号不能为空！");
                //生成订单
                String order_number = CreateOneNumberUtils.getOrderNumber();//订单号
                shoppingCartDao.createOrder(order_number,username,productNumber);//生成订单信息
                //将商品信息从购物车中删除
                shoppingCartDao.deleteProduct(username,productNumber);
                //将商品信息打上已购买
                synchronized (this)
                {
                    //查询商品是否已购买
                    String flag = shoppingCartDao.getIs_purchased(productNumber);
                    if (flag.equals("0"))
                        shoppingCartDao.is_purchased(productNumber);
                    else
                        throw new RuntimeException("该商品已经被购买！");
                    //打上购买
                }
            }
            return new ReturnInfo("200","商品结算成功！");
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//进行回滚，取消对数据库的操作
            return new ReturnInfo("500","商品结算失败！存在商品号为空或存在商品已经被购买！");
        }
    }
}
