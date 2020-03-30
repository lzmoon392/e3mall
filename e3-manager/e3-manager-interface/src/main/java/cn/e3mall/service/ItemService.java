package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;

/**
 * @author: jerry
 * @create: 2020-03-30 01:56
 */
public interface ItemService {
    /**
     * 根据ID查询商品
     * @param itemId 主键ID
     * @return cn.e3mall.service.ItemService#getItemById(long)
     */
    TbItem getItemById(long itemId);

    /**
     * 商品列表分页查询
     * @param page 当前页
     * @param size 每页数据
     * @return cn.e3mall.common.pojo.EasyUIDataGridResult
     */
    EasyUIDataGridResult getItemList(Integer page, Integer size);
}
