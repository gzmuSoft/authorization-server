package cn.edu.gzmu.authserver.auth.handler;

import cn.edu.gzmu.authserver.auth.Oauth2Helper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登录成功处理器
 *
 * @author echo
 * @version 1.0
 * @date 19-5-21 10:45
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final @NonNull Oauth2Helper oauth2Helper;
    @Setter
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        // 清除令牌操作 —— 完成
        // 最讨厌的一些东西就是某些时候解决不了，后面却可以轻松解决
        // 永远不知道第一次在写的时候想什么
        log.debug("login ~~~~~~~~~~~~");
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        if (decide(request)) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        clearOther(savedRequest, authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * 清除其他地方的登录 token
     * 只能在一个地方保持登录状态
     *
     * @param request        请求
     * @param authentication 授权
     */
    private void clearOther(SavedRequest request, Authentication authentication) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String once = "once";
        if (!parameterMap.containsKey(once)
                || parameterMap.get(once).length != 1
                || !"true".equalsIgnoreCase(parameterMap.get(once)[0])) {
            return;
        }
        String clientId = "client_id";
        if (!parameterMap.containsKey(clientId)
                || parameterMap.get(clientId).length != 1) {
            return;
        }
        clientId = parameterMap.get(clientId)[0];
        log.debug("{} clear other info from {}.", authentication.getName(), clientId);
        oauth2Helper.safeLogout(clientId, authentication);
    }

    /**
     * 决定如何继续
     *
     * @param request 请求
     * @return 结果
     */
    private Boolean decide(HttpServletRequest request) {
        return isAlwaysUseDefaultTargetUrl()
                || (getTargetUrlParameter() != null
                && StringUtils.hasText(request.getParameter(getTargetUrlParameter())));
    }

}
