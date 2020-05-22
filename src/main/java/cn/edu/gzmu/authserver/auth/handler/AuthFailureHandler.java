package cn.edu.gzmu.authserver.auth.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * 登录失败处理器
 *
 * @author echo
 * @version 1.0
 * @date 19-4-14 10:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFailureHandler implements AuthenticationFailureHandler {
    private final RedisTemplate<String, Long> longRedisTemplate;
    private static final String LOGIN_FAILURE_API_NUMBER = "login_failure_api_number";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.debug("Login failed!");
        ValueOperations<String, Long> operations = longRedisTemplate.opsForValue();
        Long number = Optional.ofNullable(operations.get(LOGIN_FAILURE_API_NUMBER)).orElse(0L);
        operations.set(LOGIN_FAILURE_API_NUMBER, number + 1);
        String username = request.getParameter("username");
        if (StringUtils.hasText(username)) {
            final String key = "failure:" + username;
            final Long userLoginSuccess = Optional.ofNullable(operations.get(key)).orElse(0L);
            operations.set(key, userLoginSuccess + 1);
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect("/oauth/login?error="
                + URLEncoder.encode(exception.getLocalizedMessage(), "UTF-8"));
    }
}
