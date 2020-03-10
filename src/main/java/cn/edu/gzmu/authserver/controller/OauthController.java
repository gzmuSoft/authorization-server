package cn.edu.gzmu.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
    public ModelAndView logoutView(
            @RequestParam("redirect_url") String redirectUrl,
            @RequestParam(name = "client_id", required = false) String clientId,
            Principal principal) {
        if (Objects.isNull(principal)) {
            return new ModelAndView(new RedirectView(redirectUrl));
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("logout");
        view.addObject("user", principal.getName());
        view.addObject("redirectUrl", redirectUrl);
        view.addObject("clientId", clientId);
        return view;
    }

}
