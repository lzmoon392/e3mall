package cn.e3mall.cart.interceptor;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录处理拦截器
 *
 * @author: jerry
 * @create: 2020-07-07 22:01
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO 前处理，执行handler之前执行此方法
        //TODO 返回true 放行 false 拦截
        String token = CookieUtils.getCookieValue(request, "token");
        if (ObjectUtils.isEmpty(token)) {
            return true;
        }
        E3Result e3Result = tokenService.getUserByToken(token);
        if (e3Result.getStatus() != 200) {
            return true;
        }
        TbUser user = (TbUser) e3Result.getData();
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //TODO handler执行之后 返回 modelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        //TODO 完成处理 返回 modelAndView之后
        //TODO 可以在此处理异常
    }
}
