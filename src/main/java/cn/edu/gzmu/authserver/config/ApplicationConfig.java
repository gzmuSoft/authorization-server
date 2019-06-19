package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.validate.ValidateCode;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
        RedisTemplate<String, ValidateCode> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.registerModule((new SimpleModule()));
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        GenericJackson2JsonRedisSerializer jacksonSerial = new GenericJackson2JsonRedisSerializer(om);
        redisTemplate.setValueSerializer(jacksonSerial);
        redisTemplate.setHashValueSerializer(jacksonSerial);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
