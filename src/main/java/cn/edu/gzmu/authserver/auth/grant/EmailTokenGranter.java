package cn.edu.gzmu.authserver.auth.grant;

import cn.edu.gzmu.authserver.auth.email.EmailUserDetailsService;
import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Objects;

/**
 * 邮箱登录
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/7/30 下午1:33
 */
public class EmailTokenGranter extends AbstractTokenGranter {
    private static final String GRANT_TYPE = SecurityConstants.GRANT_TYPE_EMAIL;
    private EmailUserDetailsService emailUserDetailsService;

    /**
     * 构造方法提供一些必要的注入的参数
     * 通过这些参数来完成我们父类的构建
     *
     * @param tokenServices tokenServices
     * @param clientDetailsService clientDetailsService
     * @param oAuth2RequestFactory oAuth2RequestFactory
     * @param emailUserDetailsService emailUserDetailsService
     */
    public EmailTokenGranter(AuthorizationServerTokenServices tokenServices,
                             ClientDetailsService clientDetailsService,
                             OAuth2RequestFactory oAuth2RequestFactory,
                             EmailUserDetailsService emailUserDetailsService) {
        super(tokenServices, clientDetailsService, oAuth2RequestFactory, GRANT_TYPE);
        this.emailUserDetailsService = emailUserDetailsService;
    }

    /**
     * 在这里查询我们用户，构建用户的授权信息
     *
     * @param client 客户端
     * @param tokenRequest tokenRequest
     * @return OAuth2Authentication
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        String email = tokenRequest.getRequestParameters().getOrDefault(GRANT_TYPE, "");
        UserDetails userDetails = emailUserDetailsService.loadUserByEmail(email);
        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        Authentication user = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        return new OAuth2Authentication(tokenRequest.createOAuth2Request(client), user);
    }
}
