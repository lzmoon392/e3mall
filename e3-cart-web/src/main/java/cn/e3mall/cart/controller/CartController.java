package cn.e3mall.cart.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-05-08 17:20
 */
@Controller
public class CartController {
    @Resource
    private ItemService itemService;
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,
                          @RequestParam(required = false, defaultValue = "1") Integer num,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (TbItem tbItem : cartList) {
            //如果存在，数量相加
            if (tbItem.getId() == itemId.longValue()) {
                flag = true;
                //找到商品 数量相加
                tbItem.setNum(tbItem.getNum() + num);
                //跳出循环
                break;
            }
        }
        //如果不存在
        if (!flag) {
            //根据商品id查询商品信息，得到一个TbItem对象
            TbItem tbItem = itemService.getItemById(itemId);
            //设置商品数量
            tbItem.setNum(num);
            //取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                tbItem.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            cartList.add(tbItem);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);
        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     * @param request
     * @return
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判空
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        //把json转换成商品列表
        return JsonUtils.jsonToList(json, TbItem.class);
    }

    /**
     * 展示购物车
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response) {
        List<TbItem> cartList = getCartListFromCookie(request);
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 更新购物车商品数量
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable final Long itemId,
                                  @PathVariable Integer num,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        //从cookie中取出购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历
        for (TbItem tbItem : cartList) {
            if (tbItem.getId().longValue() == itemId) {
                tbItem.setNum(num);
                break;
            }
        }
        //回写
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),
                COOKIE_CART_EXPIRE, true);

        return E3Result.ok();
    }

}
