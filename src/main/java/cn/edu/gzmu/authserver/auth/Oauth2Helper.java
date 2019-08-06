package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/6 下午12:41
 */
@Component
@RequiredArgsConstructor
public class Oauth2Helper {
    private final @NonNull StudentRepository studentRepository;
    private final @NonNull TeacherRepository teacherRepository;

    public Student student() {
        SysUser details = (SysUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return studentRepository.findFirstByUserId(details.getId()).orElseThrow(
                () -> new UsernameNotFoundException("找不到当前用户的学生信息"));
    }

    public Teacher teacher() {
        SysUser details = (SysUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return teacherRepository.findFirstByUserId(details.getId()).orElseThrow(
                () -> new UsernameNotFoundException("找不到当前用户的教师信息"));
    }
}
