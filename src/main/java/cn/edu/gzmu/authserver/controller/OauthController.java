package cn.edu.gzmu.authserver.controller;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 19-7-14 下午3:21
 */
@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logoutView(String redirectUrl, Principal principal) {
        if (StringUtils.isBlank(redirectUrl)) {
            throw new ResourceAccessException("资源错误，缺少必要的参数");
        }
        if (Objects.isNull(principal)) {
            throw new ResourceAccessException("资源错误，用户尚未登录");
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("logout");
        view.addObject("user", principal.getName());
        view.addObject("redirectUrl", redirectUrl);
        return view;
    }

}
