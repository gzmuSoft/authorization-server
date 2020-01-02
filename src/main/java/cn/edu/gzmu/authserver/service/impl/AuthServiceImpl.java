package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysData;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.exception.ResourceExistException;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import cn.edu.gzmu.authserver.service.AuthService;
import cn.edu.gzmu.authserver.service.SysRoleService;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

import static cn.edu.gzmu.authserver.model.constant.SecurityConstants.*;

/**
 * 授权处理
 *
 * @author echo
 * @date 19-6-18 下午10:52
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final @NonNull SysRoleService sysRoleService;
    private final @NonNull SysUserRepository sysUserRepository;
    private final @NonNull StudentRepository studentRepository;
    private final @NonNull TeacherRepository teacherRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Object register(SysUser user, Student student, SysData school) {
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        SysUser save = sysUserRepository.save(user);
        exist.setUserId(save.getId());
        studentRepository.save(exist);
        return save;
    }

    /**
     * 获取用户详细信息
     *
     * @param userId 用户 id
     * @return 结果
     */
    @Override
    public Object userDetails(Long userId) {
        SysUser user = sysUserRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("用户不存在")
        );
        Set<SysRole> roles = sysRoleService.findAllByUser(userId);
        JSONObject result = new JSONObject();
        for (SysRole role : roles) {
            switch (role.getName()) {
                case ROLE_ADMIN:
                    result.put("isAdmin", true);
                    break;
                case ROLE_TEACHER:
                    result.put("isTeacher", true);
                    result.put("teacher", teacherRepository.findFirstByUserId(user.getId()).orElse(null));
                    break;
                case ROLE_STUDENT:
                    result.put("isStudent", true);
                    result.put("student", studentRepository.findFirstByUserId(user.getId()).orElse(null));
                    break;
                default:
                    break;
            }
        }
        result.put("user", user);
        result.putIfAbsent("isAdmin", false);
        result.putIfAbsent("isTeacher", false);
        result.putIfAbsent("isStudent", false);
        return result;
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
