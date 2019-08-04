package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.Student;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午8:49
 * @deprecated 将单独抽离授权服务器数据库作为新的资源服务器
 */
@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Secured("ROLE_STUDENT")
public class StudentController {

    private @NonNull StudentRepository studentRepository;

    @GetMapping("/{id}")
    public HttpEntity<?> student(@PathVariable Long id) {
        return ResponseEntity.ok(studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The student not find by " + id))
        );
    }

    @GetMapping
    public HttpEntity<?> students(String ids) {
        Assert.notNull(ids,"缺少必要的参数信息");
        List<Student> students = studentRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, "."))
        );
        return ResponseEntity.ok(students);
    }

}