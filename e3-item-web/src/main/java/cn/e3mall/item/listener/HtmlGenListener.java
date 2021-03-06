package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jerry
 * @create: 2020-04-27 01:00
 */
public class HtmlGenListener implements MessageListener {

    @Resource
    private ItemService itemService;
    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //创建一个模板,参考jsp
            //从消息中获取商品ID
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品ID查询商品信息，商品基本信息和商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            //商品描述
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //创建一个数据集,封装商品数据
            Map data = new HashMap();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            //加载模板对象
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            //创建一个输出流，指定输出的目录及文件名（商品id+html）
            Writer out = new FileWriter(new File(HTML_GEN_PATH + itemId + ".html"));
            //生成静态页面
            template.process(data, out);
            //关闭流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
