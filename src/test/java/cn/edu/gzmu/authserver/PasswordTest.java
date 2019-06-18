package cn.edu.gzmu.authserver;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:42
 */
class PasswordTest {
    @Test
    void password() {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
