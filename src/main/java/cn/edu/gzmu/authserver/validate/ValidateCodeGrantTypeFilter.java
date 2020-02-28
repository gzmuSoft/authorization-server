package cn.edu.gzmu.authserver.validate;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.model.exception.ValidateCodeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 验证码过滤器。
 *
 * <p>继承于 {@link OncePerRequestFilter} 确保在一次请求只通过一次filter</p>
 * <p>需要配置指定拦截路径，默认拦截 POST 请求</p>
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 10:56
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeGrantTypeFilter extends OncePerRequestFilter {

    private final @NonNull AuthenticationFailureHandler authFailureHandle;
    private final @NonNull ValidateCodeProcessorHolder validateCodeProcessorHolder;
    private Map<String, ValidateCodeType> typeMap = new HashMap<>();
    private RequestMatcher tokenMatcher = new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.name());
    private RequestMatcher loginMatcher = new AntPathRequestMatcher("/authorization/form", HttpMethod.POST.name());

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 类型匹配
        typeMap.put(SecurityConstants.GRANT_TYPE_SMS, ValidateCodeType.SMS);
        typeMap.put(SecurityConstants.GRANT_TYPE_EMAIL, ValidateCodeType.EMAIL);
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (tokenMatcher.matches(request) || loginMatcher.matches(request)) {
            ValidateCodeType validateCodeType = getGrantType(request);
            if (Objects.nonNull(validateCodeType)) {
                try {
                    log.debug("请求需要验证！验证请求：" + request.getRequestURI() + "验证类型：" + validateCodeType);
                    validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                            .validate(new ServletWebRequest(request, response));
                    log.debug("验证码通过！");
                } catch (ValidateCodeException e) {
                    // 授权失败处理器接受处理
                    log.debug("验证失败！");
                    authFailureHandle.onAuthenticationFailure(request, response, e);
                    return;
                }
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     * 获取验证码类型
     *
     * @param request 请求
     * @return 验证码类型
     */
    private ValidateCodeType getGrantType(HttpServletRequest request) {
        return typeMap.get(request.getParameter(OAuth2Utils.GRANT_TYPE));
    }

}
