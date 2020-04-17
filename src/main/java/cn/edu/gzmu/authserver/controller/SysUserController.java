package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/4/17 下午1:18
 */
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(AuthConstant.SYS_USER)
public class SysUserController {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 获取用户的实体信息
     *
     * @param id id
     * @return 实体
     */
    @GetMapping("/id/{id}")
    public HttpEntity<?> userId(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findFirstByUserId(id);
        if (student.isPresent()) {
            return ok().body(student.get().setIdNumber(null));
        }
        Optional<Teacher> teacher = teacherRepository.findFirstByUserId(id);
        if (teacher.isPresent()) {
            return ok().body(teacher.get().setIdNumber(null));
        }
        return notFound().build();
    }

}
