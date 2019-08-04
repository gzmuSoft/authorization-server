package cn.edu.gzmu.authserver.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 基类
 *
 * @param <T> 实体类
 * @param <ID> 主键类型
 * @author echo
 * @version 1.0
 * @date 2019-4-11 11:59:46
 */
@NoRepositoryBean
@SuppressWarnings({"all", "uncheck"})
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 查询所有数据
     *
     * @param pageable 分页
     * @return 结果
     */
    @Query(value = "select * from #{#entityName} ", countQuery = "select count(*) from #{#entityName}", nativeQuery = true)
    Page<T> findAllExist(Pageable pageable);


    /**
     * 通过 id 列表查询
     *
     * @param ids id 列表
     * @return 结果
     */
    @Query(value = "select * from #{#entityName}  where id in (:ids) and is_enable = 1 ", nativeQuery = true)
    List<T> searchAllByIds(@Param("ids") List<String> ids);

}
