package cn.edu.gzmu.authserver.auth.sms;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权过滤器
 *
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 15:44
 * @deprecated 过于复杂的配置方式，标记过时
 */
@Deprecated
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.LOGIN_PROCESSING_URL_SMS, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String phone = obtainSms(request);
        phone = phone == null ? "" : phone.trim();
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phone);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取请求中的 sms 值
     *
     * @param request 正在为其创建身份验证请求
     * @return 请求中的 sms 值
     */
    private String obtainSms(HttpServletRequest request) {
        return request.getHeader(SecurityConstants.GRANT_TYPE_SMS);
    }

    /**
     * 提供以便子类可以配置放入 authentication request 的 details 属性的内容
     *
     * @param request     正在为其创建身份验证请求
     * @param authRequest 应设置其详细信息的身份验证请求对象
     */
    private void setDetails(HttpServletRequest request,
                            SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
