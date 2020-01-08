package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:27
 */
@Data
@Table(name = "sys_role")
@Entity(name = "sys_role")
@Where(clause = "is_enable = true")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRole extends BaseEntity implements GrantedAuthority, Serializable {

    /**
     * 描述
     */
    @Size(max = 128, message = "des 不能大于 128 位")
    private java.lang.String des;

    /**
     * 图标
     */
    @Size(max = 55, message = "iconCls 不能大于 55 位")
    private java.lang.String iconCls;

    /**
     * 父角色编号
     */
    @javax.validation.constraints.NotNull(message = "parentId 父角色编号 为必填项")
    private java.lang.Long parentId;

    @Override
    public String getAuthority() {
        return getName();
    }
}
