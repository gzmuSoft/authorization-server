package cn.edu.gzmu.authserver.validate.impl;

import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 验证码资源类
 *
 * @author echo
 * @version 1.0
 * @date 19-4-16 22:18
 */
public abstract class AbstractValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<String, ValidateCode> redisTemplate;

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
     * 构建 redis 的 key 值，需要子类实现。
     * <p>
     * 对于每种不同的验证码类型，都应该有不同的 key 的构建方式.
     * 请求中的不同的参数应该分别获取不同的属性
     *
     * @param request 需要构建的请求体，
     * @param type    验证码类型。
     * @return key
     */
    protected abstract String buildKey(ServletWebRequest request, ValidateCodeType type);
}
