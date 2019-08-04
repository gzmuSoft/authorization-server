package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/teacher")
@Secured("ROLE_TEACHER")
public class TeacherController {

    private @NonNull TeacherRepository teacherRepository;

    @GetMapping("/{id}")
    public HttpEntity<?> teacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The teacher not find by " + id))
        );
    }

    @GetMapping
    public HttpEntity<?> teachers(String ids) {
        Assert.notNull(ids,"缺少必要的参数信息");
        List<Teacher> teachers = teacherRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, "."))
        );
        return ResponseEntity.ok(teachers);
    }

}