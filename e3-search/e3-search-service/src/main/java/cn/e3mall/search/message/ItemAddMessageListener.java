package cn.e3mall.search.message;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author: jerry
 * @create: 2020-04-16 23:38
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchItemService searchItemService;
    @Override
    public void onMessage(Message message) {
        try {
            //商品中取id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息
            SearchItem item = itemMapper.getItemById(itemId);
            //创建一个文档对象
            searchItemService.addDocument(item);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
