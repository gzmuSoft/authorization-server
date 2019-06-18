package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.auth.sms.SmsAuthenticationSecurityConfig;
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
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:20
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final @NonNull ValidateCodeSecurityConfig validateCodeSecurityConfig;
    private final @NonNull SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsAuthenticationSecurityConfig);


        http.csrf()
            .disable()
            .authorizeRequests()
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/sms").permitAll();
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
