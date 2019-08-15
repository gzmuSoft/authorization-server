package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.Semester;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.SemesterRepository;
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
public class SemesterController {

    private @NonNull SemesterRepository semesterRepository;

    @GetMapping("/semester/one/{id}")
    public HttpEntity<?> semester(@PathVariable Long id) {
        Assert.notNull(id, "缺少必要的参数信息");
        return ResponseEntity.ok(semesterRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The teacher not find by " + id))
        );
    }

    @GetMapping("/semester")
    public HttpEntity<?> semesters(String ids) {
        Assert.notNull(ids, "缺少必要的参数信息");
        List<Semester> semesters = semesterRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, ","))
        );
        return ResponseEntity.ok(semesters);
    }

}