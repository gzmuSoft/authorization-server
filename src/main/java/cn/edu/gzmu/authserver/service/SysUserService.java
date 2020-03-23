package cn.edu.gzmu.authserver.service;

import cn.edu.gzmu.authserver.model.entity.SysUser;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/3/23 下午1:17
 */
public interface SysUserService {

    /**
     * 通过用户名称查询
     *
     * @param username 用户名
     * @return 结果
     */
    SysUser findByName(String username);

}
