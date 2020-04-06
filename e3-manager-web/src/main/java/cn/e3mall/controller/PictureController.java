package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jerry
 * @create: 2020-04-04 01:46
 */
@Controller
public class PictureController {
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile) {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            //把图片上传图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //得到一个图片地址和文件名
            String extName = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1);
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //补充为完整的url
            url = IMAGE_SERVER_URL + url;
            //封装到Map中返回
            resultMap.put("error", 0);
            resultMap.put("url", url);
            return JsonUtils.objectToJson(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error", 1);
            resultMap.put("message", "图片上传失败");
            return JsonUtils.objectToJson(resultMap);
        }
    }
}
