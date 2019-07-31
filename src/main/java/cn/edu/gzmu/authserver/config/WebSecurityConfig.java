package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.validate.ValidateCodeSecurityConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0.0
 * @date 19-6-11 下午5:20
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final @NonNull ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(validateCodeSecurityConfig);

        http
                .authorizeRequests()
                .antMatchers("/code/*").permitAll()
//                .antMatchers("/druid/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
