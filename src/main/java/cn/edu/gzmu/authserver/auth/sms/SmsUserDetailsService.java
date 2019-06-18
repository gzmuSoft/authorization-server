package cn.edu.gzmu.authserver.auth.sms;

import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 加载用户 sms 特定数据的核心接口。
 * <p>他在整个框架中将会作为用户数据加载。</p>
 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * DaoAuthenticationProvider}.
 *
 * <p>
 * 该接口只需要一个只读方法，这简化了对数据访问策略的支持。
 *
 * @author echo
 * @version 1.0
 * @date 19-4-20 13:30
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see UserDetails
 */
public interface SmsUserDetailsService {

    /**
     * 根据用户名找到用户。在实际实现中，只用查询手机号即可，对于手机号的验证需要对 {@link cn.edu.gzmu.authserver.validate.sms} 进行具体公职
     * 如何找寻用户具体取决于实现实例的配置方式。
     * <p>
     * 在这种情况下，返回的<code> UserDetails </code> 对象的用户名是可能是不是用户 sms 设备号。
     *
     * @param sms sms 设备号
     * @return 用户认证信息
     * @throws ResourceNotFoundException 用户名未找到
     */
    UserDetails loadUserBySms(String sms) throws ResourceNotFoundException;
}
