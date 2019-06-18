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
@ConfigurationProperties(prefix = "application.message")
public class MessageConfig {
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
}
