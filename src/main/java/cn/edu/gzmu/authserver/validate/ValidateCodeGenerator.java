package cn.edu.gzmu.authserver.validate;

import org.springframework.web.context.request.ServletWebRequest;


/**
 * 创建验证码
 *
 * @author echo
 * @version 1.0
 * @date 19-4-14 11:27
 */
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 生成结果
     */
    ValidateCode generate(ServletWebRequest request);
}
