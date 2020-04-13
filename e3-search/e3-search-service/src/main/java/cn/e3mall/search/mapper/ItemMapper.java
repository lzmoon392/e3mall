package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-13 16:47
 */
public interface ItemMapper {
    List<SearchItem> getItemList();
}
