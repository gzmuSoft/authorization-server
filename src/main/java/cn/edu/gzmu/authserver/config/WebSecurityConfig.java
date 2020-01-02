package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.auth.handler.AuthFailureHandler;
import cn.edu.gzmu.authserver.auth.handler.AuthLogoutSuccessHandler;
import cn.edu.gzmu.authserver.auth.handler.AuthSuccessHandler;
import cn.edu.gzmu.authserver.auth.res.AuthAccessDecisionManager;
import cn.edu.gzmu.authserver.handler.AccessDeniedExceptionHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0.0
 * @date 19-6-11 下午5:20
 */
@Order(2)
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final @NonNull FilterInvocationSecurityMetadataSource securityMetadataSource;
    private final @NonNull AuthAccessDecisionManager authAccessDecisionManager;
    private final @NonNull AuthFailureHandler authFailureHandler;
    private final @NonNull AuthSuccessHandler authSuccessHandler;
    private final @NonNull AuthLogoutSuccessHandler authLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 你不会相信我为了解决这个问题搞了 八 小时
        // 我也不知道为什么在某些时候总会变得很无助
        // 但是至少最后是成功的而不至于是无用功
        // 然而确是没有什么太大的收获
        http
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(securityMetadataSource);
                        o.setAccessDecisionManager(authAccessDecisionManager);
                        return o;
                    }
                }).anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
                .and()
                .formLogin()
                .loginPage("/oauth/login")
                .loginProcessingUrl("/authorization/form")
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessHandler(authLogoutSuccessHandler);
    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/css/**", "/img/**");
    }

    /**
     * 认证管理
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public OAuth2AccessDeniedHandler customAccessDeniedHandler() {
        return new AccessDeniedExceptionHandler();
    }

}
