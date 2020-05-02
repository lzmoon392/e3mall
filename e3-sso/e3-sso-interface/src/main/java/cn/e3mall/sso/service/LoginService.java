package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author: jerry
 * @create: 2020-04-30 01:21
 */
public interface LoginService {

    E3Result userLogin(String username, String password);
}
