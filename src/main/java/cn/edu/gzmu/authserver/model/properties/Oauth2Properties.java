package cn.edu.gzmu.authserver.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Oauth2 配置
 *
 * @author echo
 * @date 19-6-18 下午5:32
 */
@Data
@Component
@ConfigurationProperties("application.security")
public class Oauth2Properties {

    /**
     * token 有效期， 分钟为单位
     */
    private Long accessTokenValiditySeconds = 30L;

}