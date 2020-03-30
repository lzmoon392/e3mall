package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: jerry
 * @create: 2020-03-30 02:02
 */
@Controller
@RequestMapping(value = "/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }
}
