package cn.e3mall.cart.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-07-07 22:28
 */
public interface CartService {
    /**
     * 添加购物车
     */
    E3Result addCart(long userId, long itemId, int num);

    /**
     * 合并购物车
     */
    E3Result mergeCart(long userId, List<TbItem> cartList);

    /**
     * 获取购物车列表
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新商品数量
     */
    E3Result updateCartNum(long userId, long itemId, int num);

    /**
     * 删除商品
     */
    E3Result deleteCartItem(long userId, long itemId);
}
