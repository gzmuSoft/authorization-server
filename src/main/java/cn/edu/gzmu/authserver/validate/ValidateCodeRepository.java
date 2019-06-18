package cn.edu.gzmu.authserver.validate;


import cn.edu.gzmu.authserver.model.constant.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码资源处理
 *
 * @author echo
 * @version 1.0
 * @date 19-4-16 22:08
 */
public interface ValidateCodeRepository {

    /**
     * 保存
     *
     * @param request 请求
     * @param code    验证码
     * @param type    类型
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type);

    /**
     * 获取
     *
     * @param request 请求
     * @param type    类型
     * @return 验证码
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType type);

    /**
     * 移除
     *
     * @param request 请求
     * @param type    类型
     */
    void remove(ServletWebRequest request, ValidateCodeType type);


}
