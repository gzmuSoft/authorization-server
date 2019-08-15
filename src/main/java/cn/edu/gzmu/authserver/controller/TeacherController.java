package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.auth.Oauth2Helper;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
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
import java.util.List;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午8:49
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TeacherController {

    private @NonNull Oauth2Helper oauth2Helper;
    private @NonNull TeacherRepository teacherRepository;

    @GetMapping("/teacher/self")
    public HttpEntity<?> self() {
        return teacher(oauth2Helper.teacher().getId());
    }

    @GetMapping("/teacher/one/{id}")
    public HttpEntity<?> teacher(@PathVariable Long id) {
        Assert.notNull(id, "缺少必要的参数信息");
        return ResponseEntity.ok(teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The teacher not find by " + id))
        );
    }

    @GetMapping("/teachers")
    public HttpEntity<?> teachers(String ids) {
        Assert.notNull(ids, "缺少必要的参数信息");
        List<Teacher> teachers = teacherRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, ","))
        );
        return ResponseEntity.ok(teachers);
    }

}