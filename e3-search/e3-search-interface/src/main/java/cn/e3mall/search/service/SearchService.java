package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @author: jerry
 * @create: 2020-04-13 19:48
 */
public interface SearchService {
    SearchResult search(String keyword, Integer page, Integer rows) throws Exception;
}
