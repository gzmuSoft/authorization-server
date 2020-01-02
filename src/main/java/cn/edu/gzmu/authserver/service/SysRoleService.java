package cn.edu.gzmu.authserver.service;

import cn.edu.gzmu.authserver.model.entity.SysRole;

import java.util.List;
import java.util.Set;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午10:21
 */
public interface SysRoleService {

    /**
     * 查询所有的角色
     *
     * @param roles 角色
     * @return 结果
     */
    Set<SysRole> findAllByRoles(Set<SysRole> roles);

}
