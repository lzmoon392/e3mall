import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author: jerry
 * @create: 2020-04-13 17:29
 */
public class TestSolrJ {

    @Test
    public void addDocument() throws Exception {
        //创建一个solrServer对象，创建一个连接,参数solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://localhost:8888/solr/collection1");
        //创建一个文档对象solrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //像文档对象中添加域.文档中必须包含一个id域，所有的域的名称必须在schema.xml定义
        document.addField("id", "doc01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
}
