package cn.edu.gzmu.authserver.config;


import cn.edu.gzmu.authserver.auth.AuthTokenSecurityConfig;
import cn.edu.gzmu.authserver.validate.ValidateCodeSecurityConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0.0
 * @date 19-6-11 下午5:06
 */
@Configuration
@RequiredArgsConstructor
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final @NonNull JwtTokenStore jwtTokenStore;
    private final @NonNull ValidateCodeSecurityConfig validateCodeSecurityConfig;
    private final @NonNull AuthTokenSecurityConfig authTokenSecurityConfig;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(authTokenSecurityConfig);

        http
                .authorizeRequests()
                .antMatchers("/oauth/login").permitAll()
                .anyRequest()
                .authenticated();
    }
}
