package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.service.SysUserService;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.edu.gzmu.authserver.model.constant.SecurityConstants.*;

/**
 * 令牌增强，用于扩展
 *
 * @author echo
 * @date 19-6-19 下午3:53
 */
@Component
@RequiredArgsConstructor
public class AuthTokenEnhancer implements TokenEnhancer {

    private final @NonNull SysUserService sysUserService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>(6);
        SysUser sysUser = sysUserService.findByName(user.getUsername());
        additionalInfo.put("user_name", user.getUsername());
        additionalInfo.put("authorities", sysUser.getRoles().stream().map(SysRole::getAuthority).collect(Collectors.toList()));
        additionalInfo.put("roles", sysUser.getRoles().stream()
                .map(r -> {
                    JSONObject role = new JSONObject();
                    role.put("id", r.getId());
                    role.put("name", r.getName());
                    return role;
                }).collect(Collectors.toList())
        );
        additionalInfo.put("sub", user.getUsername());
        additionalInfo.put("user_id", sysUser.getId());
        additionalInfo.put("iat", (System.currentTimeMillis()) / 1000L);
        additionalInfo.put("nbf", (System.currentTimeMillis()) / 1000L);
        additionalInfo.put("is_student", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(ROLE_STUDENT::equals));
        additionalInfo.put("is_teacher", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).anyMatch(ROLE_TEACHER::equals));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
