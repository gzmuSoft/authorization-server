package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.config.WebSecurityConfig;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 手动授权
 * 我也不知道为什么，当配置了资源服务器以后
 * 授权码模式的登录页面只能由 spring security 进行配置
 * 在资源服务器中配置会无效。
 * 但是奇怪的是，他的安全机制依旧是 spring security 来进行资源保护
 * 初步确定是由于 {@link WebSecurityConfig} 的 {@code @Order} 的原因
 * 但是如果不配置这个，会造成授权码模式的登录页面无法自定义问题
 * 同时无法跳转到指定登录页面，所以必须添加
 *
 * 这个过滤器是为了解决 spring security 的 token 授权问题
 * 默认情况下是不会进行 token 处理的，我手动处理并进行了授权。
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/6 上午10:22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final @NonNull ResourceServerTokenServices resourceServerTokenServices;
    private final @NonNull SysUserRepository sysUserRepository;
    private final static String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(authorization) && authorization.startsWith(BEARER)) {
            String token = StringUtils.substringAfter(authorization, BEARER).trim();
            if (StringUtils.isNotBlank(token)) {
                OAuth2AccessToken oAuth2AccessToken = resourceServerTokenServices.readAccessToken(token);
                if (Objects.isNull(oAuth2AccessToken)) {
                    throw new InvalidTokenException("Token was not recognised");
                }
                if (oAuth2AccessToken.isExpired()) {
                    throw new InvalidTokenException("Token has expired");
                }
                OAuth2Authentication oAuth2Authentication = resourceServerTokenServices.loadAuthentication(token);
                oAuth2Authentication.setDetails(sysUserRepository.findFirstByName(oAuth2Authentication.getName()).orElse(null));
                SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
