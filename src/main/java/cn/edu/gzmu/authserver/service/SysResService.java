package cn.edu.gzmu.authserver.service;

import cn.edu.gzmu.authserver.model.entity.SysRes;

import java.util.List;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午9:22
 */
public interface SysResService {

    /**
     * 查所有
     *
     * @return 结果
     */
    List<SysRes> findAll();

}
