package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.auth.Oauth2Helper;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午8:49
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class StudentController {

    private @NonNull StudentRepository studentRepository;
    private @NonNull Oauth2Helper oauth2Helper;

    @GetMapping("/student/self")
    public HttpEntity<?> self() {
        return student(oauth2Helper.student().getId());
    }

    @GetMapping("/student/one/{id}")
    public HttpEntity<?> student(@PathVariable Long id) {
        Assert.notNull(id, "缺少必要的参数信息");
        return ResponseEntity.ok(studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The student not find by " + id))
        );
    }

    @GetMapping("/students")
    public HttpEntity<?> students(String ids) {
        Assert.notNull(ids, "缺少必要的参数信息");
        return ResponseEntity.ok(studentRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, ","))
        ));
    }

    @GetMapping("/students/class/{classId}")
    public HttpEntity<?> students(@PathVariable Long classId) {
        Assert.notNull(classId, "缺少必要的参数信息");
        return ResponseEntity.ok(studentRepository.findAllByClassesId(classId));
    }

}