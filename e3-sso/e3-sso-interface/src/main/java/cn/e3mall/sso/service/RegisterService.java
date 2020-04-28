package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @author: jerry
 * @create: 2020-04-28 01:15
 */
public interface RegisterService {
    E3Result checkData(String param, Integer type);

    E3Result register(TbUser tbUser);
}
