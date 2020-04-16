package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-13 16:47
 */
public interface ItemMapper {
    List<SearchItem> getItemList();

    SearchItem getItemById(@Param("itemId") Long itemId);
}
