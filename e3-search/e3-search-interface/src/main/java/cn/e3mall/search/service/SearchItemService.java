package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;

/**
 * @author: jerry
 * @create: 2020-04-13 17:41
 */
public interface SearchItemService {
    E3Result importAllItems();

    void addDocument(SearchItem item) throws Exception;
}
