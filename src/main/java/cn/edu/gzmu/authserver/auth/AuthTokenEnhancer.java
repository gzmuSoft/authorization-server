package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 令牌增强，用于扩展
 *
 * @author echo
 * @date 19-6-19 下午3:53
 */
@Component
@RequiredArgsConstructor
public class AuthTokenEnhancer implements TokenEnhancer {

    private final @NonNull SysUserRepository sysUserRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>(6);
        SysUser sysUser = sysUserRepository.findFirstByName(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("用户未找到！")
        );
        additionalInfo.put("user_name", user.getUsername());
        additionalInfo.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        additionalInfo.put("sub", user.getUsername());
        additionalInfo.put("user_id", sysUser.getId());
        // A timestamp indicating when the token was issued
        additionalInfo.put("iat", (System.currentTimeMillis()) / 1000L);
        // A timestamp indicating when the token is not to be used before
        additionalInfo.put("nbf", (System.currentTimeMillis()) / 1000L + accessToken.getExpiresIn());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
