package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-03-31 23:55
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
