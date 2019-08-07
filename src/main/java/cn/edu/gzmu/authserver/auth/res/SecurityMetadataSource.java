package cn.edu.gzmu.authserver.auth.res;

import cn.edu.gzmu.authserver.model.constant.HttpMethod;
import cn.edu.gzmu.authserver.model.entity.SysRes;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.SysResRepository;
import cn.edu.gzmu.authserver.repository.SysRoleRepository;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private final @NonNull SysResRepository sysResRepository;
    private final @NonNull SysRoleRepository sysRoleRepository;
    private final @NonNull AuthToken authToken;
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
        List<SysRes> sysRes = sysResRepository.findAll();
        SysRole publicRole = sysRoleRepository.findFirstByName(ROLE_PUBLIC).orElseThrow(
                () -> new ResourceNotFoundException("公共资源角色未找到！"));
        for (SysRes res : sysRes) {
            // 路径匹配
            if (!antPathMatcher.match(res.getUrl(), requestUrl)) {
                continue;
            }
            // 方法匹配
            if (!HttpMethod.ALL.match(res.getMethod()) && !method.equals(res.getMethod())) {
                continue;
            }
            Set<SysRole> roles = res.getRoles();
            if (CollectionUtils.isEmpty(roles)) {
                continue;
            }
            // 获取匹配当前资源的角色 id 表
            List<Long> sysRoleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
            // 如果当前匹配的角色中含有公共资源的 id
            if (sysRoleIds.contains(publicRole.getId())) {
                return SecurityConfig.createList(ROLE_PUBLIC);
            }
            List<SysRole> matchRoles = roles.stream().filter(
                    sysRole -> sysRoleIds.contains(sysRole.getId())).collect(Collectors.toList());
            if (matchRoles.size() == 0) {
                continue;
            }
            return SecurityConfig.createList(matchRoles.stream().map(SysRole::getName).collect(Collectors.joining(",")));
        }
        return SecurityConfig.createList(ROLE_NO_AUTH);
    }

    private boolean isRoleAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(ROLE_ADMIN));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }


    @Component
    @RequiredArgsConstructor
    protected static class AuthToken {

        private final @NonNull ResourceServerTokenServices resourceServerTokenServices;
        private final @NonNull SysUserRepository sysUserRepository;
        private final static String BEARER = "Bearer ";

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
    }

}
