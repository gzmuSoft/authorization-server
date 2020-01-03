package cn.edu.gzmu.authserver.auth.handler;

import cn.edu.gzmu.authserver.auth.Oauth2Helper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录处理器.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/2 下午3:11
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {
    private final @NonNull Oauth2Helper oauth2Helper;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String redirectUrl = request.getParameter("redirectUrl");
        if (StringUtils.isBlank(redirectUrl)) {
            redirectUrl = "/oauth/login";
        }
        String clientId = request.getParameter("clientId");
        if (StringUtils.isNoneBlank(clientId)) {
            oauth2Helper.safeLogout(clientId, authentication);
        }
        response.setStatus(HttpStatus.FOUND.value());
        response.sendRedirect(redirectUrl);
    }
}
