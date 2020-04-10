package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-10 22:16
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    private TbContentMapper contentMapper;

    @Override
    public E3Result addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        return E3Result.ok();
    }

    @Override
    public EasyUIDataGridResult findByPage(Long categoryId, Integer page, Integer rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> itemList = contentMapper.selectByExample(example);
        //取分页结果
        return new EasyUIDataGridResult(new PageInfo<>(itemList).getTotal(), itemList);
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        return contentMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public E3Result editContent(TbContent content) {
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContent(List<Long> ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        return E3Result.ok();
    }
}
