package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
@RepositoryRestResource(path = AuthConstant.SYS_ROLE)
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

    /**
     * 通过角色名查询
     *
     * @param name 名
     * @return 角色
     */
    @RestResource(path = "byName")
    Optional<SysRole> findFirstByName(String name);

    /**
     * 通过 id 列表查询
     *
     * @param ids id 列表
     * @return 结果
     */
    Set<SysRole> findByIdIn(List<Long> ids);

    /**
     * 角色
     *
     * @param userId 用户 id
     * @return 结果
     */
    @RestResource(path = "byUserId")
    @Query(value = "select r.* from sys_user_role sur, sys_role r " +
            "where sur.user_id = (:userId) and r.id = sur.role_id and r.is_enable = true",
            nativeQuery = true)
    Set<SysRole> searchAllByUserId(@Param("userId") Long userId);

    /**
     * 角色
     *
     * @param resId 资源 id
     * @return 结果
     */
    @RestResource(path = "byResId")
    @Query(value = "select r.* from sys_role_res srr, sys_role r " +
            "where srr.res_id = (:resId) and r.id = srr.role_id and r.is_enable = true",
            nativeQuery = true)
    Set<SysRole> searchAllByResId(@Param("resId") Long resId);

    /**
     * 获取当前角色的所有相关角色
     *
     * @param roleIds 角色 ids
     * @return 结果
     */
    @RestResource(path = "byRoleId")
    @Query(value = "WITH RECURSIVE cte as (" +
            "    SELECT * " +
            "    FROM sys_role " +
            "    WHERE id in (:roleIds) AND is_enable = true" +
            "    UNION ALL " +
            "    SELECT r.*" +
            "    FROM sys_role r " +
            "    JOIN cte c ON c.parent_id = r.id" +
            "    WHERE r.is_enable = true" +
            ") " +
            "SELECT DISTINCT * " +
            "FROM cte", nativeQuery = true)
    Set<SysRole> searchAllRoleByIds(@Param("roleIds") List<Long> roleIds);
}