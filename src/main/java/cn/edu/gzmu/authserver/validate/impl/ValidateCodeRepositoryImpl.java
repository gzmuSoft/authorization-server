package cn.edu.gzmu.authserver.validate.impl;

import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.model.exception.ValidateCodeException;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 验证码资源实现类
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/7/31 上午10:36
 */
@Component
@RequiredArgsConstructor
public class ValidateCodeRepositoryImpl implements ValidateCodeRepository {

    private final @NonNull RedisTemplate<String, ValidateCode> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, code.getExpireIn(), TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        return redisTemplate.opsForValue().get(buildKey(request, type));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * 构建 redis 存储时的 key
     *
     * @param request 请求
     * @param type 类型
     * @return key
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getParameter(type.getParamNameOnValidate().toLowerCase());
        if (StringUtils.isEmpty(deviceId)) {
            throw new ValidateCodeException("请求中不存在 " + type);
        }
        return "code:" + type + ":" + deviceId;
    }

}
