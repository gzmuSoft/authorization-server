package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import cn.edu.gzmu.authserver.util.MapUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
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
    private final SysUserRepository sysUserRepository;
    private final static String NAME = "name";
    private final static String ID = "id";

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

    @GetMapping("/info")
    public HttpEntity<?> userInfoIds(@RequestParam List<Long> ids) {
        List<JSONObject> users = sysUserRepository.findAllByIdIn(ids)
                .stream().sorted(comparingLong(SysUser::getId))
                .map(MapUtils::userBaseJson).collect(Collectors.toList());
        List<JSONObject> students = studentRepository.findAllByUserIdIn(ids).stream()
                .map(s -> {
                    final JSONObject student = new JSONObject();
                    student.put(ID, s.getUserId());
                    student.put(NAME, s.getName());
                    return student;
                }).collect(Collectors.toList());
        List<JSONObject> teachers = teacherRepository.findAllByUserIdIn(ids).stream()
                .map(t -> {
                    final JSONObject teacher = new JSONObject();
                    teacher.put(ID, t.getUserId());
                    teacher.put(NAME, t.getName());
                    return teacher;
                }).collect(Collectors.toList());
        teachers.addAll(students);
        teachers.sort(Comparator.comparing(e -> e.getLong(ID)));
        return ok(MapUtils.userBaseList(users, teachers));
    }
}
