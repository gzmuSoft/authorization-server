package cn.edu.gzmu.authserver.repository;

import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

/**
 * Teacher Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-23 17:38:13
 */
@RepositoryRestResource(path = AuthConstant.TEACHER)
public interface TeacherRepository extends BaseRepository<Teacher, Long> {

    /**
     * 通过用户 id 查询教师
     *
     * @param userId 用户 id
     * @return 结果
     */
    @RestResource(path = "byUserId")
    Optional<Teacher> findFirstByUserId(Long userId);

    /**
     * 查询在指定 user ids 的教师信息
     *
     * @param userIds 用户 ids
     * @return 学生信息
     */
    @RestResource(path = "byUserIds")
    List<Teacher> findAllByUserIdIn(List<Long> userIds);

    /**
     * 通过 DepId 查询
     *
     * @param depId depId
     * @return 列表
     */
    @RestResource(path = "byDepId")
    List<Teacher> findAllByDepId(Long depId);

}