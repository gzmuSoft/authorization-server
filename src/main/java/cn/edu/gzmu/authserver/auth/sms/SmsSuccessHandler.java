package cn.edu.gzmu.authserver.auth.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 登录成功处理器
 *
 * <p>登录成功后，生成用户的 令牌 并返回回去</p>
 *
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 19-4-14 10:45
 * @deprecated 过于复杂的配置方式，标记过时
 */
@Slf4j
//@Component
@Deprecated
@RequiredArgsConstructor
public class SmsSuccessHandler implements AuthenticationSuccessHandler {

    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull ClientDetailsService clientDetailsService;
    private final @NonNull TokenStore jwtTokenStore;
    private final @NonNull JwtAccessTokenConverter jwtAccessTokenConverter;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.debug("Login succeed！");
        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无客户端信息");
        }

        // 解密请求头
        String[] tokens = extractAndDecodeHeader(header);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 获取客户端信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("客户端信息不存在：" + clientId);
        } else if (StringUtils.equalsIgnoreCase(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("客户端密钥不匹配" + clientSecret);
        }
        response.setContentType("application/json;charset=utf-8");
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0), clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken existingAccessToken = jwtTokenStore.getAccessToken(oAuth2Authentication);
        OAuth2RefreshToken refreshToken = null;
        if (existingAccessToken != null) {
            if (existingAccessToken.isExpired()) {
                if (existingAccessToken.getRefreshToken() != null) {
                    refreshToken = existingAccessToken.getRefreshToken();
                    jwtTokenStore.removeRefreshToken(refreshToken);
                }
                jwtTokenStore.removeAccessToken(existingAccessToken);
            } else {
                jwtTokenStore.storeAccessToken(existingAccessToken, oAuth2Authentication);
                response.getWriter().write(objectMapper.writeValueAsString(existingAccessToken));
                return;
            }
        }

        if (refreshToken == null) {
            refreshToken = createRefreshToken(oAuth2Authentication);
        } else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                refreshToken = createRefreshToken(oAuth2Authentication);
            }
        }
        OAuth2AccessToken accessToken = createAccessToken(oAuth2Authentication, refreshToken);
        jwtTokenStore.storeAccessToken(accessToken, oAuth2Authentication);
        refreshToken = accessToken.getRefreshToken();
        if (refreshToken != null) {
            jwtTokenStore.storeRefreshToken(refreshToken, oAuth2Authentication);
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    /**
     * 对请求头进行解密以及解析
     *
     * @param header 请求头
     * @return 客户端信息
     */
    private String[] extractAndDecodeHeader(String header) {
        byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
        String token = new String(decoded, StandardCharsets.UTF_8);
        int delimiter = token.indexOf(":");

        if (delimiter == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delimiter), token.substring(delimiter + 1)};
    }

    /**
     * 构建 AccessToken
     *
     * @param authentication 授权信息
     * @param refreshToken 刷新 token
     * @return AccessToken
     */
    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());
        return jwtAccessTokenConverter.enhance(token, authentication);
    }

    /**
     * 创建刷新 token
     *
     * @param authentication 授权信息
     * @return 刷新 token
     */
    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
        int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
        String value = UUID.randomUUID().toString();
        if (validitySeconds > 0) {
            return new DefaultExpiringOAuth2RefreshToken(value, new Date(System.currentTimeMillis()
                    + (validitySeconds * 1000L)));
        }
        return new DefaultOAuth2RefreshToken(value);
    }

    /**
     * 获取刷新时间
     *
     * @param clientAuth 授权
     * @return 获取刷新时间
     */
    private int getRefreshTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getRefreshTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return 60 * 60 * 24 * 30;
    }

    /**
     * 获取授权时间
     *
     * @param clientAuth 李几滴按摩
     * @return 授权时间
     */
    private int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return 60 * 60 * 12;
    }
}
