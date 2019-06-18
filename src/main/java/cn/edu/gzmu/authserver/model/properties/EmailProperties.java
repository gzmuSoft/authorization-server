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
@ConfigurationProperties(prefix = "application.email")
public class EmailProperties {
    /**
     * 邮箱验证码长度
     */
    private Integer codeLength = 4;
    /**
     * 是否只要数字
     */
    private Boolean onlyNumber = false;
    /**
     * 验证码有效期
     */
    private Integer codeExpireIn = 600;
}
