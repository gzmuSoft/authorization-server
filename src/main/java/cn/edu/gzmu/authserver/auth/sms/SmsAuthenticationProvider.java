package cn.edu.gzmu.authserver.auth.sms;

import cn.edu.gzmu.authserver.auth.UserDetailsServiceImpl;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 授权提供者
 *
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 15:54
 * @deprecated 过于复杂的配置方式，标记过时
 */
@Setter
@Deprecated
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserBySms(authenticationToken.getPrincipal().toString());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无效认证");
        }
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
