package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.entity.SysData;
import cn.edu.gzmu.authserver.model.exception.ResourceNotFoundException;
import cn.edu.gzmu.authserver.repository.SysDataRepository;
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
public class SysDateController {

    private @NonNull SysDataRepository sysDataRepository;

    @GetMapping("/sysData/one/{id}")
    public HttpEntity<?> sysData(@PathVariable Long id) {
        Assert.notNull(id, "缺少必要的参数信息");
        return ResponseEntity.ok(sysDataRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The teacher not find by " + id))
        );
    }

    @GetMapping("/sysData")
    public HttpEntity<?> sysDataMany(String ids) {
        Assert.notNull(ids, "缺少必要的参数信息");
        List<SysData> sysData = sysDataRepository.searchAllByIds(Arrays.asList
                (StringUtils.split(ids, ","))
        );
        return ResponseEntity.ok(sysData);
    }

}