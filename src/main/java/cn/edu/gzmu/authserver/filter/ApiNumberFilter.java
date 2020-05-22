package cn.edu.gzmu.authserver.filter;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Oauth fitler.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/5/17 上午8:03
 */
@Component
@RequiredArgsConstructor
@Order(Integer.MIN_VALUE)
public class ApiNumberFilter extends OncePerRequestFilter {
    private static final String OAUTH_API_NUMBER = "oauth_api_number";
    private static final String AUTHORIZATION_SERVER_API_NUMBER = "authorization_server_api_number";
    private static final String AUTHORIZATION_SERVER_API_URL = "authorization_server_api_url";
    private static final String AUTHORIZATION_SERVER_DATA_API_NUMBER = "authorization_server_data_api_number";
    private final AntPathMatcher oauthPathMatcher = new AntPathMatcher("/oauth/**");
    private final RedisTemplate<String, Long> longRedisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String requestUrl = request.getServletPath();
        ValueOperations<String, Long> operations = longRedisTemplate.opsForValue();
        if (oauthPathMatcher.isPattern(requestUrl)) {
            final Long number = Optional.ofNullable(operations.get(OAUTH_API_NUMBER)).orElse(0L);
            operations.set(OAUTH_API_NUMBER, number + 1);
        }
        final Long number = Optional.ofNullable(operations.get(AUTHORIZATION_SERVER_API_NUMBER)).orElse(0L);
        operations.set(AUTHORIZATION_SERVER_API_NUMBER, number + 1);
        final String dateKey = AUTHORIZATION_SERVER_DATA_API_NUMBER + "=" + LocalDate.now().toString();
        final Long dataNumber = Optional.ofNullable(operations.get(dateKey)).orElse(0L);
        operations.set(dateKey, dataNumber + 1);
        ListOperations<String, String> stringOperations = stringRedisTemplate.opsForList();
        stringOperations.leftPush(AUTHORIZATION_SERVER_API_URL, requestUrl);
        filterChain.doFilter(request, response);
    }
}
