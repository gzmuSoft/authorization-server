package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.auth.email.EmailUserDetailsService;
import cn.edu.gzmu.authserver.auth.grant.EmailTokenGranter;
import cn.edu.gzmu.authserver.auth.grant.SmsTokenGranter;
import cn.edu.gzmu.authserver.auth.sms.SmsUserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0.0
 * @date 19-6-11 下午5:06
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final @NonNull AuthenticationManager authenticationManager;
    private final @NonNull TokenStore jdbcTokenStore;
    private final @NonNull JwtTokenStore jwtTokenStore;
    private final @NonNull ClientDetailsService clientDetails;
    private final @NonNull JwtAccessTokenConverter jwtAccessTokenConverter;
    private final @NonNull TokenEnhancer authTokenEnhancer;
    private final @NonNull SmsUserDetailsService smsUserDetailsService;
    private final @NonNull EmailUserDetailsService emailUserDetailsService;
    private final @NotNull UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(authTokenEnhancer, jwtAccessTokenConverter));

        endpoints.authenticationManager(authenticationManager)
            .tokenStore(jwtTokenStore)
            .tokenStore(jdbcTokenStore)
            .tokenEnhancer(tokenEnhancerChain)
            .userDetailsService(userDetailsService);
        endpoints.tokenGranter(tokenGranter(endpoints));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 先获取已经有的五种授权，然后添加我们自己的进去
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new SmsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), smsUserDetailsService));
        granters.add(new EmailTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), emailUserDetailsService));
        return new CompositeTokenGranter(granters);
    }
}
