package cn.edu.gzmu.authserver.auth.res;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static cn.edu.gzmu.authserver.model.constant.SecurityConstants.*;

/**
 * 授权决策
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/6 下午2:03
 */
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();
            if (ROLE_NO_AUTH.equals(needRole)) {
                throw new AccessDeniedException("权限不足");
            }
            // 如果是 ROLE_NO_LOGIN 资源且是匿名用户，放行
            if (ROLE_NO_LOGIN.equals(needRole)
                    && roleCondition(authentication, ROLE_ANONYMOUS)) {
                return;
            }
            // 如果是 ROLE_PUBLIC 资源且不是匿名用户，放行
            if (ROLE_PUBLIC.equals(needRole)
                    && !roleCondition(authentication, ROLE_ANONYMOUS)) {
                return;
            }
            // 符合条件的，放行
            if (roleCondition(authentication, needRole)) {
                return;
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private Boolean roleCondition(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(role));
    }
}
