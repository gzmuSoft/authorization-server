package cn.edu.gzmu.authserver.validate.impl;

import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import cn.edu.gzmu.authserver.validate.ValidateCode;
import cn.edu.gzmu.authserver.validate.ValidateCodeGenerator;
import cn.edu.gzmu.authserver.validate.ValidateCodeProcessor;
import cn.edu.gzmu.authserver.validate.ValidateCodeRepository;
import cn.edu.gzmu.authserver.model.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import javax.xml.bind.ValidationException;
import java.util.Map;

/**
 * 默认抽象的验证码处理器，不同类型的验证码必须继承此类。
 * <p>
 * 提供了一套默认完整的生成、保存操作,对于不同的类型会有不同的发送操作，因此子类必须实现
 * 抽象 <code>send</code> 方法
 * 具体请参阅每个方法的详细注释
 *
 * @author echo
 * @version 1.0
 * @date 19-4-14 11:37
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode>
        implements ValidateCodeProcessor {

    private static final String CODE = "code";

    @Autowired
    private Map<String, ValidateCodeRepository> validateCodeRepositoryMap;

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    /**
     * 验证码创建逻辑
     *
     * @param request 请求
     * @throws Exception 异常
     */
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C generate = generate(request);
        save(request, generate);
        send(request, generate);
    }

    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 验证码
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) throws ValidationException {
        String generatorName = getComponentName(ValidateCodeGenerator.class);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidationException("验证码生成器" + generatorName + "不存在。");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 验证器验证验证码
     *
     * @param request 被验证的请求
     */
    @Override
    @SuppressWarnings("unchecked")
    public void validate(ServletWebRequest request) {
        String repositoryName = getComponentName(ValidateCodeRepository.class);
        ValidateCodeType validateCodeType = getValidateCodeType();
        ValidateCodeRepository validateRepository = validateCodeRepositoryMap.get(repositoryName);
        C code = (C) validateRepository.get(request, validateCodeType);
        if (code == null){
            throw new ValidateCodeException("获取验证码失败，请检查邮箱号是否正确或重新发送！");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), request.getHeader(CODE))) {
            throw new ValidateCodeException("验证码不正确，请重新输入！");
        }
        // 暂时不清除验证码
        // validateRepository.remove(request, validateCodeType);
    }

    /**
     * 保存验证码
     *
     * @param request      请求
     * @param validateCode 验证码
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCodeRepository validateRepository = validateCodeRepositoryMap.get(getComponentName(ValidateCodeRepository.class));
        validateRepository.save(request, validateCode, getValidateCodeType());
    }

    /**
     * 发送验证码，由子类实现
     *
     * @param request      请求
     * @param validateCode 验证码
     * @throws Exception 异常
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求 url 获取验证码类型
     *
     * @return 结果
     */
    private ValidateCodeType getValidateCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    private String getComponentName(Class component) {
        return getValidateCodeType().toString().toLowerCase() + component.getSimpleName();
    }
}
