package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-09 23:05
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory category : categoryList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent() ? "closed" : "open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        //1正常 2删除
        category.setStatus(1);
        category.setSortOrder(1);
        //新节点一定是子节点
        category.setIsParent(false);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        tbContentCategoryMapper.insert(category);
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
        }
        tbContentCategoryMapper.updateByPrimaryKey(parent);
        return E3Result.ok(category);
    }

    @Override
    public E3Result deleteContentCategoryById(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> categories = tbContentCategoryMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(categories)) {
            return E3Result.build(401, "删除失败,请逐级删除");
        }
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = category.getParentId();

        tbContentCategoryMapper.deleteByPrimaryKey(id);

        TbContentCategoryExample example1 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andParentIdEqualTo(parentId);
        List<TbContentCategory> categories2 = tbContentCategoryMapper.selectByExample(example1);


        if (CollectionUtils.isEmpty(categories2)) {
            TbContentCategory category1 = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            category1.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(category1);
        }

        return E3Result.ok();
    }

    @Override
    public E3Result updateContentCategoryById(Long id, String name) {
        TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(category)) {
            return E3Result.build(401, "分类不存在");
        }
        category.setName(name);
        tbContentCategoryMapper.updateByPrimaryKey(category);
        return E3Result.ok();
    }
}
