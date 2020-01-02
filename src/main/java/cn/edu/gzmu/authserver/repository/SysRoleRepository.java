package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * SysRole Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-7 11:05:31
 */
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

    /**
     * 通过角色名查询
     *
     * @param name 名
     * @return 角色
     */
    Optional<SysRole> findFirstByName(String name);

    /**
     * 通过 id 列表查询
     *
     * @param ids id 列表
     * @return 结果
     */
    Set<SysRole> findByIdIn(List<Long> ids);

}