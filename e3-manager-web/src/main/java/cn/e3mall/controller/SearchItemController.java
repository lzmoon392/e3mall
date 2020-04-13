package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author: jerry
 * @create: 2020-04-13 18:11
 */
@Controller
public class SearchItemController {
    @Resource
    private SearchItemService searchItemService;
    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importItemList() {
        return searchItemService.importAllItems();
    }
}
