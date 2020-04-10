package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-03-30 02:02
 */
@Controller
public class ItemController {
    @Resource
    private ItemService itemService;

    @RequestMapping(value = "/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public E3Result saveItem(TbItem item, String desc) {
        itemService.addItem(item, desc);
        return E3Result.ok();
    }

    @RequestMapping(value = "/item/edit", method = RequestMethod.POST)
    @ResponseBody
    public E3Result editItem(TbItem item, String desc) {
        itemService.editItem(item, desc);
        return E3Result.ok();
    }

    @RequestMapping("/item/desc/{itemId}")
    @ResponseBody
    public E3Result getItemDesc(@PathVariable Long itemId) {
        return itemService.getItemDesc(itemId);
    }

    @RequestMapping("/item/delete")
    @ResponseBody
    public E3Result deleteItem(@RequestParam List<Long> ids) {
        return itemService.deleteItem(ids);
    }

    @RequestMapping("/item/instock")
    @ResponseBody
    public E3Result instockItem(@RequestParam List<Long> ids) {
        return itemService.updateItem(ids, (byte) 2);
    }
    @RequestMapping("/item/reshelf")
    @ResponseBody
    public E3Result reshelfItem(@RequestParam List<Long> ids) {
        return itemService.updateItem(ids, (byte) 1);
    }

}
