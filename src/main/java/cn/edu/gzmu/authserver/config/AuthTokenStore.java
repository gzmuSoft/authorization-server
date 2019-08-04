package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.model.properties.Oauth2Properties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * token 配置
 *
 * @author echo
 * @date 19-6-18 下午8:05
 */
@Configuration
@RequiredArgsConstructor
public class AuthTokenStore {
    private final @NonNull DataSource dataSource;
    private final @NonNull Oauth2Properties oauth2Properties;
    private final @NonNull TokenEnhancer authTokenEnhancer;

    /**
     * TokenServices
     *
     * @return Token 配置
     */
    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setClientDetailsService(clientDetails());
        defaultTokenServices.setTokenEnhancer(authTokenEnhancer);
        defaultTokenServices.setAccessTokenValiditySeconds((int)
                TimeUnit.MINUTES.toSeconds(oauth2Properties.getAccessTokenValiditySeconds()));
        return defaultTokenServices;
    }

    /**
     * 声明 jdbc TokenStore实现
     *
     * @return JdbcTokenStore
     */
    @Bean
    @Primary
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 声明 jwt TokenStore实现
     *
     * @return JdbcTokenStore
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * jwt 令牌 配置，非对称加密
     *
     * @return 转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("gzmu.jks"), "lizhongyue248".toCharArray());
        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("gzmu"));
        return accessTokenConverter;
    }

    /**
     * 声明 ClientDetails实现
     *
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

}