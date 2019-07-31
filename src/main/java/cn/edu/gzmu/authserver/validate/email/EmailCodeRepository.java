package cn.edu.gzmu.authserver.validate.email;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.model.exception.ValidateCodeException;
import cn.edu.gzmu.authserver.validate.impl.AbstractValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-5-22 9:02
 * @deprecated 过于复杂的实现方式，标记过时
 */
//@Component("emailValidateCodeRepository")
@Deprecated
public class EmailCodeRepository extends AbstractValidateCodeRepository {
    @Override
    protected String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String email = request.getHeader(SecurityConstants.GRANT_TYPE_EMAIL);
        if (StringUtils.isBlank(email)) {
            throw new ValidateCodeException("请求中不存在邮箱号");
        }
        return "code:" + type.toString().toLowerCase() + ":" + email;
    }
}
