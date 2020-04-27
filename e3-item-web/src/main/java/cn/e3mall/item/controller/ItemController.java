package cn.e3mall.item.controller;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author: jerry
 * @create: 2020-04-17 23:09
 */
@Controller
public class ItemController {

    @Resource
    private ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }

}
