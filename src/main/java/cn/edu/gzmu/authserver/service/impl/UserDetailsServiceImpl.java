package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.auth.email.EmailUserDetailsService;
import cn.edu.gzmu.authserver.auth.sms.SmsUserDetailsService;
import cn.edu.gzmu.authserver.config.Oauth2AuthorizationServerConfig;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.service.SysRoleService;
import cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static cn.edu.gzmu.authserver.model.constant.UserStatus.isEnable;
import static cn.edu.gzmu.authserver.model.constant.UserStatus.isLocked;

/**
 * 用户服务，根据不同的策略查询不同的用户。
 * <p>通过实现不同的接口完成认证</p>
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 10:49
 * @see Oauth2AuthorizationServerConfig
 */
@Slf4j
@RequiredArgsConstructor
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService, SmsUserDetailsService, EmailUserDetailsService {

    private final @NonNull SysUserRepository sysUserRepository;
    private final @NonNull SysRoleService sysRoleService;

    /**
     * 通过用户名查找用户，这是对密码登录的仅有支持。
     *
     * <p>
     * 在对应的容器中，他会自己调用 {@link UserDetailsService} 接口实现
     * </p>
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户位置哦到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("password login user: {}", username);
        // 由于用户不复杂所以不在去自己构建 UserDetails 的实现类了。
        return loadUser(() -> sysUserRepository.findFirstByNameOrPhoneOrEmail(username, username, username));
    }

    /**
     * 通过设备号查找用户，这是对设备登录的仅有支持
     * <p>
     * 注意，在这里并不会验证密码，sms 登录仅对验证吗进行验证
     * 具体验证规则见 {@link AbstractValidateCodeProcessor}
     * 需要修改请继承此类并覆盖其方法
     *
     * @param sms sms 设备号
     * @return 用户
     * @throws ResourceNotFoundException sms 未找到异常
     */
    @Override
    public UserDetails loadUserBySms(String sms) {
        log.debug("sms login user: {}", sms);
        return loadUser(() -> sysUserRepository.findFirstByPhone(sms));
    }


    /**
     * 通过设备号查找用户，这是对设备登录的仅有支持
     * <p>
     * 注意，在这里并不会验证密码，sms 登录仅对验证吗进行验证
     * 具体验证规则见 {@link AbstractValidateCodeProcessor}
     * 需要修改请继承此类并覆盖其方法
     *
     * @param email email 设备号
     * @return 用户
     * @throws ResourceNotFoundException sms 未找到异常
     */
    @Override
    public UserDetails loadUserByEmail(String email) throws ResourceNotFoundException {
        log.debug("email login user: {}", email);
        return loadUser(() -> sysUserRepository.findFirstByEmail(email));
    }

    /**
     * 加载用户，具体加载操作通过函数式接口进行完成，由调用方提供。
     *
     * @param load Supplier，直接获取一个结果
     * @return 用户
     */
    private User loadUser(Supplier<Optional<SysUser>> load) {
        SysUser sysUser = load.get().orElseThrow(() -> new UsernameNotFoundException("用户 %s 不存在"));
        Set<SysRole> sysRoles = sysRoleService.findAllByUser(sysUser.getId());
        return new User(sysUser.getName(), sysUser.getPassword(),
                !isEnable(sysUser.getStatus()), true, true,
                !isLocked(sysUser.getStatus()), sysRoles);
    }

}
