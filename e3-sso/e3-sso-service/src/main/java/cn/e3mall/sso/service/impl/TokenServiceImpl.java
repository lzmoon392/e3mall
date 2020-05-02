package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jerry
 * @create: 2020-04-30 21:25
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;
    @Resource
    private JedisClient jedisClient;


    @Override
    public E3Result getUserByToken(String token) {
        String result = jedisClient.get("SESSION:" + token);
        if (StringUtils.isBlank(result)) {
            return E3Result.build(201, "用户登录已过期");
        }
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        TbUser user = JsonUtils.jsonToPojo(result, TbUser.class);
        return E3Result.ok(user);
    }

}
