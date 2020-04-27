package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;


/**
 * SysUser Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-7 11:05:31
 */
@RepositoryRestResource(path = AuthConstant.SYS_USER)
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    /**
     * Find All By Ids
     *
     * @param ids Ids
     * @return all
     */
    List<SysUser> findAllByIdIn(List<Long> ids);

    /**
     * 通过名称查询用户，并非模糊查询。
     * 每次获取均需要通过非空判断。
     *
     * @param name 名称
     * @return 结果
     */
    @RestResource(exported = false)
    Optional<SysUser> findFirstByName(String name);

    /**
     * 通过手机号查询用户，并非模糊查询。
     * 每次获取均需要通过非空判断。
     *
     * @param phone 手机号
     * @return 结果
     */
    @RestResource(exported = false)
    Optional<SysUser> findFirstByPhone(String phone);

    /**
     * 通过邮箱号查询用户，并非模糊查询。
     * 每次获取均需要通过非空判断。
     *
     * @param email 邮箱号
     * @return 结果
     */
    @RestResource(exported = false)
    Optional<SysUser> findFirstByEmail(String email);

    /**
     * 通过用户名、手机号、邮箱号查询用户，并非模糊查询。
     * 每次获取均需要通过非空判断。
     *
     * @param name 用户名
     * @param phone 手机号
     * @param email 邮箱号
     * @return 结果
     */
    @RestResource(exported = false)
    Optional<SysUser> findFirstByNameOrPhoneOrEmail(String name, String phone, String email);


    /**
     * 通过用户名、手机号、邮箱号查询是否存在
     *
     * @param name 用户名
     * @param phone 手机号
     * @param email 邮箱号
     * @return 结果
     */
    @RestResource(path = "exist")
    Boolean existsByNameOrPhoneOrEmail(String name, String phone, String email);

    /**
     * 通过名称查询是否存在
     *
     * @param name 名称
     * @return 结果
     */
    @RestResource(path = "existByName")
    Boolean existsByName(String name);

    /**
     * 通过手机号查询是否存在
     *
     * @param phone phone
     * @return 结果
     */
    @RestResource(path = "existByPhone")
    Boolean existsByPhone(String phone);

    /**
     * 通过邮箱查询是否存在
     *
     * @param email email
     * @return 结果
     */
    @RestResource(path = "existByEmail")
    Boolean existsByEmail(String email);

}