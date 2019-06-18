package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.validate.ValidateCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author echo
 * @date 19-6-18 下午11:04
 */
@Configuration
public class ApplicationConfig {

    /**
     * 操作 redis
     *
     * @param redisConnectionFactory redis 链接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, ValidateCode> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ValidateCode> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory);
        redis.afterPropertiesSet();
        return redis;
    }

}
