package cn.edu.gzmu.authserver.model;

import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户的测试，对于所有的用户相关的测试都会在这里进行
 *
 * @author echo
 * @date 19-6-18 下午4:15
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SysUserTest {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Test
    void testGetAll() {
        List<SysUser> all = sysUserRepository.findAll();
        assertNotNull(all);
        all.forEach(System.out::println);
        SysUser sysUser = all.get(0);
        sysUser.getRoles().forEach(System.out::println);
    }
}
