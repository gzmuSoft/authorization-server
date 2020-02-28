package cn.edu.gzmu.authserver.validate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 验证码安全配置
 *
 * 添加过滤器
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 16:19
 */
@Component
@RequiredArgsConstructor
public class ValidateCodeSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final @NonNull ValidateCodeGrantTypeFilter validateCodeGrantTypeFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(validateCodeGrantTypeFilter,
                UsernamePasswordAuthenticationFilter.class);
    }
}
