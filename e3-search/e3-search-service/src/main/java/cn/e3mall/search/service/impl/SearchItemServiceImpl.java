package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-13 17:42
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Resource
    private ItemMapper itemMapper;
    @Resource
    private SolrServer solrServer;

    @Override
    public E3Result importAllItems() {
        try {
            List<SearchItem> itemList = itemMapper.getItemList();
            for (SearchItem item : itemList) {
                addDocument(item);
            }
            solrServer.commit();
            return E3Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "数据导入时发生异常");
        }
    }

    public void addDocument(SearchItem item) throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", item.getId());
        document.addField("item_title", item.getTitle());
        document.addField("item_sell_point", item.getSell_point());
        document.addField("item_price", item.getPrice());
        document.addField("item_image", item.getImage());
        document.addField("item_category_name", item.getCategory_name());
        solrServer.add(document);
    }

}
