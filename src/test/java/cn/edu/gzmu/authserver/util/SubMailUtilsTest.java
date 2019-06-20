package cn.edu.gzmu.authserver.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author echo
 * @version 1.0
 * @date 19-4-14 16:23
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName(":iphone: 手机短信测试")
class SubMailUtilsTest {

    @Autowired
    private SubMailUtils subMailUtils;

    @Test
    @DisplayName(":iphone: 向指定手机号发送一条短信")
    void application() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "登录");
        jsonObject.put("code", "123456");
        jsonObject.put("time", "123");
        subMailUtils.sendActionMessage("13765308262", jsonObject);
    }
}
