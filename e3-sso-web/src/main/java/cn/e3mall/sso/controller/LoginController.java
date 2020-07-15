package cn.e3mall.sso.controller;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jerry
 * @create: 2020-04-29 01:11
 */
@Controller
public class LoginController {

    @Resource
    private LoginService loginService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request, HttpServletResponse response) {
        E3Result result = loginService.userLogin(username, password);
        if (200 == result.getStatus()) {
            String token = result.getData().toString();
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        return E3Result.ok();
    }
}
