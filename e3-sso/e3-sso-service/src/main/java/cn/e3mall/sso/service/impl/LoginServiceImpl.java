package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author: jerry
 * @create: 2020-04-30 01:23
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private TbUserMapper userMapper;

    @Override
    public E3Result userLogin(String username, String password) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = userMapper.selectByExample(example);
        if (ObjectUtils.isEmpty(userList)) {
            return E3Result.build(400, "用户名或密码错误");
        }
        TbUser user = userList.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return E3Result.build(400, "用户名或密码错误");
        }
        user.setPassword(null);
        String token = UUID.randomUUID().toString();
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        return E3Result.ok(token);
    }

}
