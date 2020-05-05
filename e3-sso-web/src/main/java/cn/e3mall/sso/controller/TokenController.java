package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author: jerry
 * @create: 2020-05-02 14:33
 */
@Controller
public class TokenController {
    @Resource
    private TokenService tokenService;

    //    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public String getUserByToken(@PathVariable String token, @RequestParam(required = false) String callback) {
//        E3Result result = tokenService.getUserByToken(token);
//        if (StringUtils.isNotBlank(callback)) {
//            return callback + "(" + JsonUtils.objectToJson(result) + ");";
//        }
//        return JsonUtils.objectToJson(result);
//    }
    //Spring 4.1以后启用
    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, @RequestParam(required = false) String callback) {
        E3Result result = tokenService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

}
