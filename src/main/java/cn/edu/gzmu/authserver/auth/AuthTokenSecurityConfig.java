package cn.edu.gzmu.authserver.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/6 上午10:32
 */
@Component
@RequiredArgsConstructor
public class AuthTokenSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final @NonNull AuthTokenFilter authTokenFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(authTokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }
}
