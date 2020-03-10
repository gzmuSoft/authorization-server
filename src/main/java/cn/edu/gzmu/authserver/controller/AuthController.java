package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysData;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.service.AuthService;
import cn.edu.gzmu.authserver.util.VerifyParameter;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * 授权信息
 *
 * @author echo
 * @version 1.0
 * @date 19-4-16 20:46
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final @NonNull AuthService authService;
    private final @NonNull PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @VerifyParameter(
            required = {
                    "user.name#用户名称不能为空！",
                    "student.id#学生id为必填项！",
                    "student.name#学生名称为必填项！",
                    "user.email#用户邮箱为必填项！",
                    "user.phone#用户手机号为必填项！",
                    "school.id#学校为必填项！"
            },
            equal = {"school.type|1#选择的数据类型必须为学校类型！"}
    )
    public HttpEntity<?> register(@NotNull @RequestBody JSONObject params) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.register(
                        params.getObject("user", SysUser.class),
                        params.getObject("student", Student.class),
                        params.getObject("school", SysData.class)
                )
        );
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

    @GetMapping("/password")
    public HttpEntity<?> password(@RequestParam String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }

    @GetMapping("/match")
    public HttpEntity<?> match(@RequestParam String password, @RequestParam String encode) {
        return ResponseEntity.ok(passwordEncoder.matches(password, encode));
    }

}
