package cn.edu.gzmu.authserver;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:42
 */
public class PasswordTest {
    @Test
    public void password() {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }
}
