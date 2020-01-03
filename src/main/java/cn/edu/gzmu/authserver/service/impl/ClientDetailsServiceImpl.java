package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.service.ClientRegistrationService;
import cn.edu.gzmu.authserver.model.entity.ClientDetails;
import cn.edu.gzmu.authserver.repository.ClientDetailsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.List;
import java.util.Objects;

/**
 * 自定义 jdbc 读取客户端信息.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @version 1.0
 * @date 2019/12/23 下午10:07
 */
@Slf4j
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService {

    private final @NonNull ClientDetailsRepository clientDetailsRepository;
    private final @NonNull PasswordEncoder passwordEncoder;
    private static final String NO_CLIENT = "No client found with id = ";

    @Override
    public org.springframework.security.oauth2.provider.ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        org.springframework.security.oauth2.provider.ClientDetails clientDetails = clientDetailsRepository
                .findFirstByClientId(clientId).orElseThrow(() -> new NoSuchClientException(NO_CLIENT + clientId))
                .buildSpringClientDetails();
        log.debug("获取客户端信息：{}", clientDetails);
        return clientDetails;
    }

    @Override
    public void saveOrUpdateClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException, NoSuchClientException {
        if (Objects.isNull(clientDetails.getId())) {
            log.debug("添加客户端信息：{}", clientDetails);
            clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        } else {
            log.debug("更新客户端信息：{}", clientDetails);
            ClientDetails client = clientDetailsRepository.findById(clientDetails.getId())
                    .orElseThrow(() -> new NoSuchClientException(NO_CLIENT + clientDetails.getId()));
            clientDetails.setClientSecret(client.getClientSecret());
        }
        clientDetailsRepository.save(clientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException {
        ClientDetails client = clientDetailsRepository
                .findFirstByClientId(clientId).orElseThrow(() -> new NoSuchClientException(NO_CLIENT + clientId));
        client.setClientSecret(passwordEncoder.encode(clientSecret));
        clientDetailsRepository.save(client);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        ClientDetails client = clientDetailsRepository
                .findFirstByClientId(clientId).orElseThrow(() -> new NoSuchClientException(NO_CLIENT + clientId));
        client.setIsEnable(false);
        clientDetailsRepository.save(client);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return clientDetailsRepository.findAll();
    }

}
