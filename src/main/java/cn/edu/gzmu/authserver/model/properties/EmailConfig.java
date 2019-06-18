package cn.edu.gzmu.authserver.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Japoul
 * @version 1.0
 * @date 2019-05-21 15:46
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {
    /**
     * host
     */
    private String host;
    /**
     * port
     */
    private String port;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
}
