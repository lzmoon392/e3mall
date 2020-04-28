package cn.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: jerry
 * @create: 2020-04-29 01:11
 */
@Controller
public class LoginController {
    @RequestMapping("/page/login")
    public String login() {
        return "login";
    }
}
