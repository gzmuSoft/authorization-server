package cn.edu.gzmu.authserver.auth.res;

import cn.edu.gzmu.authserver.model.constant.HttpMethod;
import cn.edu.gzmu.authserver.model.entity.SysRes;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.service.SysResService;
import cn.edu.gzmu.authserver.service.SysRoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static cn.edu.gzmu.authserver.model.constant.SecurityConstants.*;

/**
 * 动态权限配置核心，将会对请求进行进行匹配
 * <p>
 * 对请求匹配后，赋予对应的角色。
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/6 下午1:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final @NonNull SysResService sysResService;
    private final @NonNull SysRoleService sysRoleService;
    private final @NonNull AuthToken authToken;
    private static final String ALL = "all";
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
        authToken.authorization(httpRequest);
        if (isRoleAdmin()) {
            return SecurityConfig.createList(ROLE_PUBLIC);
        }
        String method = httpRequest.getMethod();
        String requestUrl = httpRequest.getServletPath();
        List<SysRes> sysRes = sysResService.findAll();
        Collection<String> scopes = authToken.scopes();
        for (SysRes res : sysRes) {
            // 路径匹配
            if (!antPathMatcher.match(res.getUrl(), requestUrl)) {
                continue;
            }
            // 方法匹配
            if (!HttpMethod.ALL.match(res.getMethod()) && !method.equals(res.getMethod())) {
                continue;
            }
            Set<SysRole> roles = sysRoleService.findAllByRes(res.getId());
            if (CollectionUtils.isEmpty(roles)) {
                continue;
            }
            // 获取匹配当前资源的角色 id 表
            List<String> sysRoleNames = roles.stream()
                    .map(SysRole::getName)
                    .collect(Collectors.toList());
            if (sysRoleNames.contains(ROLE_PUBLIC)) {
                return SecurityConfig.createList(ROLE_PUBLIC);
            } else if (sysRoleNames.contains(ROLE_NO_LOGIN)) {
                return SecurityConfig.createList(ROLE_NO_LOGIN);
            }
            if (!containIgnoreCase(scopes, res.getScopes().toUpperCase())) {
                continue;
            }
            return SecurityConfig.createListFromCommaDelimitedString(
                    String.join(",", sysRoleNames));

        }
        log.debug("{} {} 权限不足", method, requestUrl);
        return SecurityConfig.createList(ROLE_NO_AUTH);
    }

    /**
     * 是否是管理员
     *
     * @return 结果
     */
    private boolean isRoleAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(ROLE_ADMIN));
    }

    /**
     * 过滤一次 scopes
     *
     * @param scopes     scopes
     * @param needScopes 需要的 scopes
     * @return 是否通过
     */
    private Boolean containIgnoreCase(Collection<String> scopes, String needScopes) {
        return scopes.stream()
                .map(String::toUpperCase)
                .anyMatch(needScopes::contains)
                || scopes.stream()
                .map(String::toUpperCase)
                .anyMatch(scope -> scope.equalsIgnoreCase(ALL));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


    /**
     * 网页登录的时候，是使用模板引擎的非前后端分离方案
     * 所以他不会将自己作为资源服务器去请求解析token，
     * 在这里需要手动解析一下 token，并设置到安全上下文中
     */
    @Component
    @RequiredArgsConstructor
    protected static class AuthToken {

        private final @NonNull ResourceServerTokenServices resourceServerTokenServices;
        private final @NonNull SysUserRepository sysUserRepository;
        private static final String BEARER = "Bearer ";

        void authorization(HttpServletRequest request) {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.isNull(authorization) || !authorization.startsWith(BEARER)) {
                return;
            }
            String token = StringUtils.substringAfter(authorization, BEARER).trim();
            if (StringUtils.isBlank(token)) {
                return;
            }
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

        Collection<String> scopes() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof OAuth2Authentication)) {
                return Collections.emptyList();
            }
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
            return oauth2Authentication.getOAuth2Request().getScope();
        }

    }

}
