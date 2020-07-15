package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jerry
 * @create: 2020-07-14 22:22
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, "token");
        if (ObjectUtils.isEmpty(token)) {
            response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        E3Result e3Result = tokenService.getUserByToken(token);
        if (e3Result.getStatus() != 200) {
            response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        TbUser user = (TbUser) e3Result.getData();
        request.setAttribute("user", user);
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (!ObjectUtils.isEmpty(json)) {
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(json, TbItem.class));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
