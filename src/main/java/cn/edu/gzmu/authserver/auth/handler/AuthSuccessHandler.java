package cn.edu.gzmu.authserver.auth.handler;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理器
 *
 * @author echo
 * @version 1.0
 * @date 19-5-21 10:45
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final @NonNull TokenStore tokenStore;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // TODO: 刷新令牌操作 —— 待完成
        // 最讨厌的一些东西就是某些时候解决不了，后面却可以轻松解决
        // 永远不知道第一次在写的时候想什么
        log.debug("login ~~~~~~~~~~~~");
    }
}
