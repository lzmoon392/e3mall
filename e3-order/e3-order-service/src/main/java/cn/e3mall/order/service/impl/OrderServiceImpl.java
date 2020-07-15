package cn.e3mall.order.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-07-15 23:13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private TbOrderMapper orderMapper;
    @Resource
    private TbOrderItemMapper orderItemMapper;
    @Resource
    private TbOrderShippingMapper orderShippingMapper;
    @Resource
    private JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;


    @Override
    public E3Result createOrder(OrderInfo orderInfo) {
        //生成订单号 redis incr
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
            jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //补全orderInfo属性
        orderInfo.setOrderId(orderId);
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderInfo);
        //向订单明细表插入数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            orderItem.setId(jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString());
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }
        //向订单物流表插入数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        //返回E3Result，包含订单号
        return E3Result.ok(orderId);
    }
}
