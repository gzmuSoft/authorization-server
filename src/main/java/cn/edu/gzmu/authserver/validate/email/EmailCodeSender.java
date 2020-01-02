package cn.edu.gzmu.authserver.validate.email;

import cn.edu.gzmu.authserver.model.properties.EmailProperties;
import cn.edu.gzmu.authserver.util.EmailUtils;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author echo
 * @version 1.0
 * @date 19-5-21 23:52
 */
@Slf4j
@RequiredArgsConstructor
public class EmailCodeSender implements ValidateCodeSender {

    private final @NonNull EmailUtils emailUtils;
    private final @NonNull EmailProperties emailProperties;

    @Override
    public void send(String receive, ValidateCode code) {
        HashMap<String, Object> variables = new HashMap<>(1);
        variables.put("code", code.getCode());
        variables.put("time", Duration.ofSeconds(code.getExpireIn()).toMinutes());
        if (emailProperties.getDev()) {
            log.info("向 {} 发送邮箱登录验证码 {}，有效期 {} 分钟。", receive, code.getCode(),
                    Duration.ofSeconds(code.getExpireIn()).toMinutes());
        } else {
            log.debug("向 {} 发送邮箱登录验证码 {}，有效期 {} 分钟。", receive, code.getCode(),
                    Duration.ofSeconds(code.getExpireIn()).toMinutes());
            emailUtils.sendTemplateMail(receive, "登录",
                    "[贵州民族大学]欢迎登录", "registerTemplate.html", variables);
        }
    }
}
