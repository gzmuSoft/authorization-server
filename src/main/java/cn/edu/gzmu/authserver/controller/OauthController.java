package cn.edu.gzmu.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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

    @GetMapping("/me")
    @PreAuthorize("isFullyAuthenticated()")
    public HttpEntity<?> me(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
