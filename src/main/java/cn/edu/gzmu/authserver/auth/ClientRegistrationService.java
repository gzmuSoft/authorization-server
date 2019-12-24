package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.entity.ClientDetails;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.List;

/**
 * 自定义客户端服务.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/23 下午11:20
 */
public interface ClientRegistrationService {

    /**
     * 添加客户端
     *
     * @param clientDetails 客户端
     * @throws ClientAlreadyExistsException 客户端已存在异常
     * @throws NoSuchClientException        找不到客户端异常
     */
    void saveOrUpdateClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException, NoSuchClientException;

    /**
     * 更新客户端蜜意奥
     *
     * @param clientId     客户端 ID
     * @param clientSecret 客户端 密钥
     * @throws NoSuchClientException 未找到异常
     */
    void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException;

    /**
     * 移除客户端
     *
     * @param clientId 客户端 ID
     * @throws NoSuchClientException 未找到异常
     */
    void removeClientDetails(String clientId) throws NoSuchClientException;

    /**
     * 获取所有客户端
     *
     * @return 客户端
     */
    List<ClientDetails> listClientDetails();

}
