package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jerry
 * @create: 2020-04-24 00:07
 */
@Controller
public class HtmlGenController {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws Exception{
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //加载模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //创建一个数据集
        Map map = new HashMap();
        map.put("hello", "hello freemarker");
        //指定文件输出路径以及文件名
        Writer out = new FileWriter(new File("/Users/liuwei/studay/e3mall/e3-item-web/src/main/webapp/WEB-INF/freemarker/hello2.txt"));
        template.process(map,out);
        out.close();
        return "OK";
    }
}
