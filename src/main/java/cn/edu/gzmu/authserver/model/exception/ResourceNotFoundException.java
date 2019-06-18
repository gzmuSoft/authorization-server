package cn.edu.gzmu.authserver.model.exception;

import cn.edu.gzmu.authserver.auth.sms.SmsUserDetailsService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

/**
 * 如果 {@link SmsUserDetailsService} 实现无法通过其 sms 设备号找到{@link User}，则抛出此异常。
 *
 * @author echo
 * @version 1.0
 * @date 19-4-20 13:40
 */
public class ResourceNotFoundException extends AuthenticationException {
    /**
     * 构造自定义信息的 <code> ResourceNotFoundException </code>。
     *
     * @param msg 细节信息
     */
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    /**
     * 构造自定义信息异常的 <code> ResourceNotFoundException </code>。
     *
     * @param msg 细节信息
     * @param t   异常
     */
    public ResourceNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
