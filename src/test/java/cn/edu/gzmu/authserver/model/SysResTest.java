package cn.edu.gzmu.authserver.model;

import cn.edu.gzmu.authserver.model.entity.SysRes;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.repository.SysResRepository;
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
class SysResTest {
    @Autowired
    private SysResRepository sysResRepository;

    @Test
    void testGetAll() {
        List<SysRes> all = sysResRepository.findAll();
        all.forEach(System.out::println);
        all.forEach(a -> a.getRoles().forEach(System.out::println));
    }
}
