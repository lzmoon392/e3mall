package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author: jerry
 * @create: 2020-04-13 20:08
 */
@Controller
public class SearchController {
    @Resource
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemLost(@RequestParam String keyword,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 Model model) throws Exception {
        keyword = new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recordCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
        return "search";

    }
}
