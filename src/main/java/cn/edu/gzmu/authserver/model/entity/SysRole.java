package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:27
 */
@Data
@Table(name = "sys_role")
@Entity(name = "sys_role")
@Where(clause = "is_enable = 1")
@ToString(callSuper = true, exclude = "users")
@EqualsAndHashCode(callSuper = true, exclude = "users")
@Accessors(chain = true)
public class SysRole extends BaseEntity implements Serializable {

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

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<SysUser> users = new HashSet<>();

    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_res", joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "resId"))
    private Set<SysRes> res = new HashSet<>();

}
