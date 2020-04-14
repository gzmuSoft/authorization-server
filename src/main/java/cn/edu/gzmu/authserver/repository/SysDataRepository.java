package cn.edu.gzmu.authserver.repository;

import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.SysData;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * SysData Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-23 17:38:13
 */
@RepositoryRestResource(path = AuthConstant.SYS_DATA)
public interface SysDataRepository extends BaseRepository<SysData, Long> {

    /**
     * 通过父级id和类型查询
     *
     * @param parentId 父级id
     * @param type 类型
     * @return 结果
     */
    @RestResource(path = "/byParentIdAndType")
    List<SysData> findAllByParentIdAndType(Long parentId, Integer type);

}