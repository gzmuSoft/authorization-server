package cn.edu.gzmu.authserver.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Japoul
 * @author echo
 * @version 1.0
 * @date 2019-05-21 16:45
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class EmailUtilsTest {

    @Autowired
    private EmailUtils emailUtils;

    @Test
    @DisplayName("\uD83D\uDCE9 邮件发送测试")
    void application() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("code", RandomCode.random(6, false));
        values.put("time", 10);
        String toEmail = "lizhongyue248@163.com";
        String type = "注册";
        String subject = "云课程注册邮件";
        String template = "registerTemplate.html";
        Future<String> res = emailUtils.sendTemplateMail(toEmail, type, subject, template, values);
        assertTrue(res.isDone());
    }
}
