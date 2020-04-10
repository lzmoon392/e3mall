package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-09 23:02
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);

    E3Result addContentCategory(Long parentId, String name);

    E3Result deleteContentCategoryById(Long id);

    E3Result updateContentCategoryById(Long id, String name);
}
