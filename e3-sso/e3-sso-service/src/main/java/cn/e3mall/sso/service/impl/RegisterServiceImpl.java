package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: jerry
 * @create: 2020-04-28 01:17
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private TbUserMapper userMapper;

    @Override
    public E3Result checkData(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1：用户名 2：手机号 3：邮箱
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if (3 == type) {
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400, "数据类型错误");
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword()) ||
                StringUtils.isBlank(tbUser.getPhone())) {
            return E3Result.build(400, "用户数据不完整,注册失败");
        }
        E3Result result = checkData(tbUser.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return E3Result.build(400, "用户名已经被占用");
        }
        result = checkData(tbUser.getPhone(), 2);
        if (!(boolean) result.getData()) {
            return E3Result.build(400, "手机号已经被占用");
        }
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        tbUser.setEmail(null);
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        int count = userMapper.insert(tbUser);
        if (count > 0) {
            return E3Result.ok();
        }
        return E3Result.build(400, "注册失败");
    }

}
