package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.constant.EntityType;
import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysData;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.exception.ResourceExistException;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 授权处理
 *
 * @author echo
 * @date 19-6-18 下午10:52
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final @NonNull SysUserRepository sysUserRepository;
    private final @NonNull StudentRepository studentRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    Object register(SysUser user, Student student, SysData school) {
        Student exist = studentRepository.findById(student.getId()).orElseThrow(() ->
                new ResourceNotFoundException("学生资源不存在！"));
        Assert.isNull(exist.getUserId(), "当前学生已注册！");
        if (!exist.getSchoolId().equals(school.getId()) || !exist.getNo().equals(student.getNo())
                || !exist.getIdNumber().equals(student.getIdNumber()) || !exist.getName().equals(student.getName())) {
            throw new ResourceNotFoundException("学生信息不匹配！");
        }
        if (existUser(user)) {
            throw new ResourceExistException("用户已经存在！");
        }
        user.setEntityType(EntityType.STUDENT.value());
        user.setEntityId(student.getId());
        user.setPwd(bCryptPasswordEncoder.encode(user.getPwd()));
        SysUser save = sysUserRepository.save(user);
        exist.setUserId(save.getId());
        studentRepository.save(exist);
        return save;
    }


    /**
     * 是否存在用户
     *
     * @param user 用户
     * @return 结果
     */
    private boolean existUser(SysUser user) {
        Assert.notNull(user.getName(), "用户名不能为空");
        SysUser sysUser = new SysUser();
        sysUser.setName(user.getName());
        if (sysUserRepository.exists(Example.of(sysUser))) {
            return true;
        }
        Assert.notNull(user.getEmail(), "邮箱不能为空");
        sysUser = new SysUser();
        sysUser.setEmail(user.getEmail());
        if (sysUserRepository.exists(Example.of(sysUser))) {
            return true;
        }
        Assert.notNull(user.getPhone(), "手机号不能为空");
        sysUser = new SysUser();
        sysUser.setPhone(user.getPhone());
        return sysUserRepository.exists(Example.of(sysUser));
    }
}
