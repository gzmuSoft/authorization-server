package cn.edu.gzmu.authserver;

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
     * 重要！！！jwt签名密钥，不可公开！
     */
    private String jwtSigningKey;

    /**
     * token 有效期， 分钟为单位
     */
    private Long accessTokenValiditySeconds = 30L;
}