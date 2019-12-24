package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户端信息.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 2019/12/23 下午10:11
 */
@Data
@ToString(callSuper = true)
@Table(name = "client_details")
@Entity(name = "client_details")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ClientDetails extends BaseEntity implements Serializable {
    /**
     * client id
     */
    @javax.validation.constraints.NotNull(message = "clientId 为必填项")
    private String clientId;

    /**
     * client 密钥
     */
    @javax.validation.constraints.NotNull(message = "clientSecret 为必填项")
    private String clientSecret;

    /**
     * 资源编号
     */
    @javax.validation.constraints.NotNull(message = "resourceIds 为必填项")
    private String resourceIds;

    /**
     * 授权域
     */
    @javax.validation.constraints.NotNull(message = "scope 为必填项")
    private String scope;

    /**
     * 授权类型
     */
    @javax.validation.constraints.NotNull(message = "grantTypes 为必填项")
    private String grantTypes;

    /**
     * 重定向地址，授权码时必填
     */
    private String redirectUrl;

    /**
     * 授权信息
     */
    private String authorities;

    /**
     * 授权令牌有效时间
     */
    @javax.validation.constraints.NotNull(message = "accessTokenValidity 为必填项")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    @javax.validation.constraints.NotNull(message = "refreshTokenValidity 为必填项")
    private Integer refreshTokenValidity;

    /**
     * 保留字段
     */
    private String additionalInformation = "{}";

    /**
     * 自动授权请求域
     */
    private String autoApproveScopes;

    public org.springframework.security.oauth2.provider.ClientDetails buildSpringClientDetails() {
        BaseClientDetails details = new BaseClientDetails(clientId, resourceIds, scope, grantTypes, authorities, redirectUrl);
        details.setClientSecret(clientSecret);
        details.setAutoApproveScopes(Collections.singletonList(autoApproveScopes));
        details.setAccessTokenValiditySeconds(accessTokenValidity);
        details.setRefreshTokenValiditySeconds(refreshTokenValidity);
        details.setAdditionalInformation(JSON.parseObject(additionalInformation));
        return details;
    }

    public static ClientDetails buildFromSpring(org.springframework.security.oauth2.provider.ClientDetails clientDetails) {
        return new ClientDetails()
                .setClientId(clientDetails.getClientId())
                .setClientSecret(clientDetails.getClientSecret())
                .setScope(String.join(",", clientDetails.getScope()))
                .setGrantTypes(String.join(",", clientDetails.getAuthorizedGrantTypes()))
                .setRedirectUrl(String.join(",", clientDetails.getRegisteredRedirectUri()))
                .setResourceIds(String.join(",", clientDetails.getResourceIds()))
                .setAuthorities(clientDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .setAccessTokenValidity(clientDetails.getAccessTokenValiditySeconds())
                .setRefreshTokenValidity(clientDetails.getRefreshTokenValiditySeconds())
                .setAdditionalInformation(JSON.toJSONString(clientDetails.getAdditionalInformation()));
    }
}
