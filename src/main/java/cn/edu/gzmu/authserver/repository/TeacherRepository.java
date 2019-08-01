package cn.edu.gzmu.authserver.repository;


import cn.edu.gzmu.authserver.base.BaseRepository;
import cn.edu.gzmu.authserver.model.entity.Teacher;

import java.util.Optional;

/**
 * Teacher Repository
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-23 17:38:13
 */
public interface TeacherRepository extends BaseRepository<Teacher, Long> {

    /**
     * 通过用户 id 查询教师
     *
     * @param userId 用户 id
     * @return 结果
     */
    Optional<Teacher> findFirstByUserId(Long userId);

}