package cn.edu.gzmu.authserver.validate;

import cn.edu.gzmu.authserver.model.constant.SecurityConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 动态获取验证码
 *
 * @author echo
 * @version 1.0
 * @date 19-4-14 13:59
 */
@RestController
@RequiredArgsConstructor
public class ValidateCodeController {

    private final @NonNull ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 通过 type 进行查询到对对应的处理器
     * 同时创建验证码
     *
     * @param request  请求
     * @param response 响应
     * @param type     验证码类型
     * @throws Exception 异常
     */
    @GetMapping(SecurityConstants.VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void creatCode(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type)
                .create(new ServletWebRequest(request, response));
    }

}
