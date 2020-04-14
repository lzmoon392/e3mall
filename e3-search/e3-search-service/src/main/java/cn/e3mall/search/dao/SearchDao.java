package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: jerry
 * @create: 2020-04-13 19:29
 */
@Repository
public class SearchDao {
    @Resource
    private SolrServer solrServer;
    /**
     * 根据条件搜索
     * @param query 条件
     * @return cn.e3mall.common.pojo.SearchResult
     */
    public SearchResult search(SolrQuery query) throws Exception{
        SearchResult result = new SearchResult();
        List<SearchItem> searchItemList = new ArrayList<>();
        //根据query查询数据库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //高亮
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //取查询结果总记录数
        result.setRecordCount(solrDocumentList.getNumFound());
        //取商品列表需要高亮显示
        for (SolrDocument document : solrDocumentList) {
            SearchItem item = new SearchItem();
            item.setId(String.valueOf(document.get("id")));
            item.setCategory_name(String.valueOf(document.get("item_category_name")));
            item.setImage(String.valueOf(document.get("item_image")));
            item.setPrice((Long) document.get("item_price"));
            item.setSell_point(String.valueOf(document.get("item_sell_point")));
            //取高亮
            List<String> list = highlighting.get(document.get("id").toString()).get("item_title");
            if (!CollectionUtils.isEmpty(list)) {
                item.setTitle(list.get(0));
            } else {
                item.setTitle(String.valueOf(document.get("item_title")));
            }

            searchItemList.add(item);
        }
        //返回结果
        result.setItemList(searchItemList);
        return result;
    }
}
