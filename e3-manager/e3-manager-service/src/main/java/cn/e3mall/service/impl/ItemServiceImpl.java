package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-03-30 01:58
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Resource
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @Override
    public TbItem getItemById(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUIDataGridResult getItemList(@RequestParam Integer page, @RequestParam Integer size) {
        //设置分页信息
        PageHelper.startPage(page, size);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        //取分页结果
        return new EasyUIDataGridResult(new PageInfo<>(itemList).getTotal(), itemList);
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品ID,
        final long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //补全item属性
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建描述表对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向描述表插入数据
        itemDescMapper.insert(itemDesc);
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(String.valueOf(itemId));
            }
        });
        return E3Result.ok();
    }

    @Override
    public E3Result getItemDesc(Long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return E3Result.ok(itemDesc);
    }

    @Override
    public E3Result editItem(TbItem item, String desc) {
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKey(item);
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescMapper.updateByPrimaryKey(itemDesc);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(List<Long> ids) {
        for (Long id : ids) {
            itemMapper.deleteByPrimaryKey(id);
        }
        return E3Result.ok();
    }

    @Override
    public E3Result updateItem(List<Long> ids, Byte status) {
        for (Long id : ids) {
            TbItem tbItem = itemMapper.selectByPrimaryKey(id);
            tbItem.setStatus(status);
            itemMapper.updateByPrimaryKey(tbItem);
        }
        return E3Result.ok();
    }

}
