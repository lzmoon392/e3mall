package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @author: jerry
 * @create: 2020-07-15 23:12
 */
public interface OrderService {
    E3Result createOrder(OrderInfo orderInfo);
}
