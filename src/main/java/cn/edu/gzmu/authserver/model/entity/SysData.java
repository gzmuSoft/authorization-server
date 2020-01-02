package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:29
 */
@Data
@ToString(callSuper = true)
@Table(name = "sys_data")
@Entity(name = "sys_data")
@Where(clause = "is_enable = true")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysData extends BaseEntity implements Serializable {

    /**
     * 0，代表无上级，即：学校
     */
    private java.lang.Long parentId;

    /**
     * 简介
     */
    @Size(max = 2048, message = "brief 不能大于 2048 位")
    private java.lang.String brief;

    /**
     * 0：学校，1：学院，2：系部，3：专业，4：班级，5：性别，6：学历，7：学位，8：教师毕业专业，9：民族，10：研究方向，11：职称
     */
    private java.lang.Integer type;

    @Transient
    private SysData parent;
}
