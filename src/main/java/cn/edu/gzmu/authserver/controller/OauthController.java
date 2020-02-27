package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
    private final @NonNull AuthService authService;

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


    @GetMapping("/me")
    @PreAuthorize("isFullyAuthenticated()")
    public HttpEntity<?> me(Principal principal) {
        if (!(principal instanceof OAuth2Authentication)) {
            return ResponseEntity.badRequest().build();
        }
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        SysUser details = (SysUser) authentication.getDetails();
        return ResponseEntity.ok(authService.userDetails(details.getId()));
    }

}
