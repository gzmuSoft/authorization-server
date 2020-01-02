package cn.edu.gzmu.authserver.model.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author echo
 * @version 1.0
 * @date 19-5-7 17:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.sms")
public class SmsConfig {
    /**
     * app id
     */
    private String appId;
    /**
     * app key
     */
    private String appKey;
    /**
     * 注册/登录 模板
     */
    private String actionTemplate;
    /**
     * 短信验证码长度
     */
    private Integer codeLength = 4;
    /**
     * 验证码有效期
     */
    private Integer codeExpireIn = 60;
    /**
     * 是否开发模式
     */
    private Boolean dev = false;
}
