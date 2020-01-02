package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.model.entity.SysRes;
import cn.edu.gzmu.authserver.repository.SysResRepository;
import cn.edu.gzmu.authserver.service.SysResService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午9:23
 */
@Service
@RequiredArgsConstructor
public class SysResServiceImpl implements SysResService {
    private final @NonNull SysResRepository sysResRepository;

    @Override
    public List<SysRes> findAll() {
        return sysResRepository.findAll();
    }

}
