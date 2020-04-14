package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jerry
 * @create: 2020-04-13 19:51
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, Integer page, Integer rows) throws Exception {
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyword);
        //设置分页条件
        if (page <= 0) {
            page = 1;
        }
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df", "item_title");
        //开启高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        //调用DAO执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算总页数
        Long recordCount = searchResult.getRecordCount();
        Integer totalPage = (int) (recordCount / rows);
        if (recordCount % rows > 0) {
            totalPage++;
        }
        searchResult.setTotalPages(totalPage);
        //返回结果
        return searchResult;
    }
}
