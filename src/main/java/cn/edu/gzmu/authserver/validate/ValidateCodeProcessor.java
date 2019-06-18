package cn.edu.gzmu.authserver.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器，封装不同验证码的处理逻辑
 *
 * @author echo
 * @version 1.0
 * @date 19-4-14 11:30
 */
public interface ValidateCodeProcessor {

    /**
     * 创建验证码
     *
     * @param request 请求
     * @throws Exception 异常
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 验证验证码
     *
     * @param request 请求
     */
    void validate(ServletWebRequest request);
}
