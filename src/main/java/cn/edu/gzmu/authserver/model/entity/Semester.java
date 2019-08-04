package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * semester
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-27 10:59:08
 */
@Data
@ToString(callSuper = true)
@Table(name = "semester")
@Entity(name = "semester")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Semester extends BaseEntity implements Serializable {

    /**
     * 学校编号
     */
    @javax.validation.constraints.NotNull(message = "schoolId 学校编号 为必填项")
    private Long schoolId;

    /**
     * 起始日期
     */
    private java.time.LocalDate startDate;

    /**
     * 结束日期
     */
    private java.time.LocalDate endDate;

    /**
     * 学校
     */
    @Transient
    private SysData school;
}