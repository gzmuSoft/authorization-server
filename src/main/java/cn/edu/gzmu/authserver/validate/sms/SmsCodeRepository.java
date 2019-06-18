package cn.edu.gzmu.authserver.validate.sms;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.model.exception.ValidateCodeException;
import cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author echo
 * @version 1.0
 * @date 19-4-17 11:04
 */
@Component("smsValidateCodeRepository")
public class SmsCodeRepository extends AbstractValidateCodeRepository {

    @Override
    protected String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader(SecurityConstants.PARAMETER_SMS);
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请求中不存在设备号");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

}
