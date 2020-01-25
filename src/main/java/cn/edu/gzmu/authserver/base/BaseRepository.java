package cn.edu.gzmu.authserver.base;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 基类
 *
 * @param <T>  实体类
 * @param <ID> 主键类型
 * @author echo
 * @version 1.0
 * @date 2019-4-11 11:59:46
 */
@NoRepositoryBean
@SuppressWarnings({"all", "uncheck"})
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 获取所有
     *
     * @return list
     */
    @RestResource(path = "all", rel = "all")
    @Query(value = "select * from #{#entityName}", nativeQuery = true)
    List<T> searchAll();

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
    @RestResource(path = "byIds")
    @Query(value = "select * from #{#entityName}  where id in (:ids) and is_enable = true ", nativeQuery = true)
    List<T> searchAllByIds(@NotNull @Param("ids") List<Long> ids);


    /**
     * 通过 id 列表查询
     *
     * @param ids      id 列表
     * @param pageable 分页对象
     * @return 结果
     */
    @RestResource(path = "byIdsPage", rel = "byIdsPage")
    @Query(value = "select * from #{#entityName}  where id in (:ids) and is_enable = true ",
            countQuery = "select count(*) from #{#entityName}", nativeQuery = true)
    Page<T> searchAllByIds(@NotNull @Param("ids") List<ID> ids, Pageable pageable);


    /**
     * 删除多个数据.
     *
     * @param ids ids
     */
    @Modifying
    @RestResource(path = "deleteByIds")
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update #{#entityName} set is_enable = false where id in (:ids)", nativeQuery = true)
    int deleteExistByIds(@NotNull @Param("ids") List<ID> ids);

}
