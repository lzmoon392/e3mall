package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author: jerry
 * @create: 2020-04-30 21:24
 */
public interface TokenService {
    /**
     * 根据token查询用户信息
     *
     * @param token token
     * @return cn.e3mall.common.utils.E3Result
     */
    E3Result getUserByToken(String token);
}
