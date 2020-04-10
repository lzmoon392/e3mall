package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-10 22:20
 */
@Controller
public class ContentController {
    @Resource
    private ContentService contentService;

    @RequestMapping(value = "/content/query/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult findByPage(@RequestParam Long categoryId,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "20") Integer rows) {
        return contentService.findByPage(categoryId, page, rows);
    }

    @RequestMapping(value = "/content/save", method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent content) {
        return contentService.addContent(content);
    }

    @RequestMapping(value = "/content/edit", method = RequestMethod.POST)
    @ResponseBody
    public E3Result editContent(TbContent content) {
        return contentService.editContent(content);
    }
    @RequestMapping(value = "/content/delete", method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteContent(@RequestParam List<Long> ids) {
        return contentService.deleteContent(ids);
    }
}
