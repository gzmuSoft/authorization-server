package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.auth.ClientDetailsServiceImpl;
import cn.edu.gzmu.authserver.model.properties.Oauth2Properties;
import cn.edu.gzmu.authserver.repository.ClientDetailsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
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
    private final @NonNull Oauth2Properties oauth2Properties;
    private final @NonNull TokenEnhancer authTokenEnhancer;
    private final @NonNull RedisConnectionFactory redisConnectionFactory;
    private final @NonNull ClientDetailsRepository clientDetailsRepository;
    private static final Integer STRENGTH = 12;

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
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
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
        accessTokenConverter.setKeyPair(keyPair());
        return accessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("gzmu.jks"), "lizhongyue248".toCharArray());
        return keyStoreKeyFactory.getKeyPair("gzmu");
    }

    /**
     * 加密.
     *
     * @return 加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }


    /**
     * 声明 客户端 信息
     *
     * @return ClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new ClientDetailsServiceImpl(clientDetailsRepository, passwordEncoder());
    }

}