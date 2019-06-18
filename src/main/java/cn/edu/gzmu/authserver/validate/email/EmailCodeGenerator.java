package cn.edu.gzmu.authserver.validate.email;

import cn.edu.gzmu.authserver.model.properties.EmailProperties;
import cn.edu.gzmu.authserver.util.RandomCode;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author echo
 * @version 1.0
 * @date 19-5-21 23:52
 */
@Component("emailValidateCodeGenerator")
@RequiredArgsConstructor
public class EmailCodeGenerator implements ValidateCodeGenerator {
    private final @NonNull EmailProperties emailProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        return new ValidateCode(RandomCode.random(emailProperties.getCodeLength(), emailProperties.getOnlyNumber()),
                emailProperties.getCodeExpireIn());
    }
}
