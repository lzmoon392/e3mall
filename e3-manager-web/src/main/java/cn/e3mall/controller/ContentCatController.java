package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-09 23:18
 */
@Controller
public class ContentCatController {

    @Resource
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCatList(parentId);
    }

    @RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(@RequestParam Long parentId, @RequestParam String name) {
        return contentCategoryService.addContentCategory(parentId, name);
    }

    @RequestMapping(value = "/content/category/delete/", method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteContentCategory(@RequestParam Long id) {
        return contentCategoryService.deleteContentCategoryById(id);
    }

    @RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateContentCategory(@RequestParam Long id, @RequestParam String name) {
        return contentCategoryService.updateContentCategoryById(id, name);
    }
}
