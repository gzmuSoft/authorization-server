package cn.edu.gzmu.authserver;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:42
 */
class PasswordTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Test
    void password() {
        System.out.println(passwordEncoder.encode("lesson-cloud-secret"));
        System.out.println(passwordEncoder.encode("secret"));
        System.out.println(passwordEncoder.encode("1997"));
    }

    @Test
    void base64() {
        String s = Base64.getEncoder().encodeToString("client:secret".getBytes());
        System.out.println(s);
    }
}
