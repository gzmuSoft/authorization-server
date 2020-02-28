package cn.edu.gzmu.authserver.config;

import cn.edu.gzmu.authserver.validate.ValidateCode;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpHeaders;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.ManagedType;

/**
 * @author echo
 * @date 19-6-18 下午11:04
 */
@Configuration
@AllArgsConstructor
@EnableRedisHttpSession
public class ApplicationConfig implements RepositoryRestConfigurer {

    private final @NonNull EntityManagerFactory entityManagerFactory;

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
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.registerModule((new SimpleModule()));
        Jackson2JsonRedisSerializer<ValidateCode> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(ValidateCode.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
//        GenericJackson2JsonRedisSerializer jacksonSerial = new GenericJackson2JsonRedisSerializer(om);
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 暴露所有实体id.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfiguration) {
        entityManagerFactory.getMetamodel().getManagedTypes().stream()
                .filter(managedType -> managedType.getJavaType().isAnnotationPresent(Entity.class))
                .map(ManagedType::getJavaType)
                .forEach(restConfiguration::exposeIdsFor);
        restConfiguration.getExposureConfiguration()
                .disablePutForCreation()
                .disablePutOnItemResources();
        restConfiguration.getCorsRegistry()
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.ORIGIN)
                .allowCredentials(true)
                .maxAge(3600);
    }

}
