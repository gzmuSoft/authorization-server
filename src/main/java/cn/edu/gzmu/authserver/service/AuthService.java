package cn.edu.gzmu.authserver.service;

import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysData;
import cn.edu.gzmu.authserver.model.entity.SysUser;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午9:26
 */
public interface AuthService {
    /**
     * 注册
     *
     * @param user    用户
     * @param student 学生
     * @param school  学校
     * @return 结果
     */
    Object register(SysUser user, Student student, SysData school);

    /**
     * 用户信息
     *
     * @param userId 用户 id
     * @return 结果
     */
    Object userDetails(Long userId);
}
