package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.SysRes;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * SysRole Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-7 11:05:31
 */
@RepositoryRestResource(path = AuthConstant.SYS_RES)
public interface SysResRepository extends BaseRepository<SysRes, Long> {
}