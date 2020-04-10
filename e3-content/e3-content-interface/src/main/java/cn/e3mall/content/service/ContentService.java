package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-10 22:15
 */
public interface ContentService {
    E3Result addContent(TbContent content);

    EasyUIDataGridResult findByPage(Long categoryId, Integer page, Integer rows);

    List<TbContent> getContentListByCid(Long cid);

    E3Result editContent(TbContent content);

    E3Result deleteContent(List<Long> ids);
}
