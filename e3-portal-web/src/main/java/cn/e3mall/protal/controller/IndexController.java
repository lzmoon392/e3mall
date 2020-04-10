package cn.e3mall.protal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-08 22:56
 */
@Controller
public class IndexController {
    @Resource
    private ContentService contentService;
    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;
    @RequestMapping("index")
    public String showIndex(Model model) {
        List<TbContent> contentList = contentService.getContentListByCid(CONTENT_LUNBO_ID);
        model.addAttribute("ad1List", contentList);
        return "index";
    }
}
