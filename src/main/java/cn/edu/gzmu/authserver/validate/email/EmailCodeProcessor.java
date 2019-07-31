package cn.edu.gzmu.authserver.validate.email;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeSender;
import cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author echo
 * @version 1.0
 * @date 19-5-21 23:52
 */
@Component("emailValidateCodeProcessor")
@RequiredArgsConstructor
public class EmailCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    private final @NonNull ValidateCodeSender mailCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        mailCodeSender.send(request.getParameter(SecurityConstants.GRANT_TYPE_EMAIL), validateCode);
    }
}
