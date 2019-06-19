package cn.edu.gzmu.authserver.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author echo
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

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(authTokenEnhancer, jwtAccessTokenConverter));

        endpoints.authenticationManager(authenticationManager)
            .tokenStore(jwtTokenStore)
            .tokenStore(jdbcTokenStore)
            .tokenEnhancer(tokenEnhancerChain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
