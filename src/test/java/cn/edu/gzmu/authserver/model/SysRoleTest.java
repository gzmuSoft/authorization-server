package cn.edu.gzmu.authserver.model;

import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.repository.SysRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 角色测试相关
 *
 * @author echo
 * @date 19-6-18 下午5:07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SysRoleTest {
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Test
    void testGetAll() {
        List<SysRole> all = sysRoleRepository.findAll();
        assertNotNull(all);
        all.forEach(System.out::println);
        SysRole sysRole = all.get(0);
        sysRole.getUsers().forEach(System.out::println);
    }
}
