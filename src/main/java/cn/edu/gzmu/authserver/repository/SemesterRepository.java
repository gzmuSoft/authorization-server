package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.Semester;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Semester Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-23 17:38:13
 */
@RepositoryRestResource(path = AuthConstant.SEMESTER)
public interface SemesterRepository extends BaseRepository<Semester, Long> {

}