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
import java.util.Set;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:26
 */
@Data
@Table(name = "sys_user")
@Entity(name = "sys_user")
@Where(clause = "is_enable = 1")
@ToString(callSuper = true, exclude = "roles")
@EqualsAndHashCode(callSuper = true, exclude = "roles")
@Accessors(chain = true)
public class SysUser extends BaseEntity implements Serializable {

    /**
     * 用户主体编号
     */
    private java.lang.Long entityId;

    /**
     * 0：系统管理员、1：教务管理员、2：课程管理员、3：教师、4：学生
     */
    private java.lang.Integer entityType;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    @javax.validation.constraints.NotNull(message = "pwd 密码 为必填项")
    @Size(max = 255, message = "pwd 不能大于 255 位")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.lang.String pwd;

    /**
     * 1：正常、2：锁定一小时、3：禁用
     */
    @javax.validation.constraints.NotNull(message = "status 1：正常、2：锁定一小时、3：禁用 为必填项")
    private java.lang.Integer status;

    /**
     * 图标
     */
    @Size(max = 255, message = "icon 不能大于 255 位")
    private java.lang.String icon;

    /**
     * 电子邮箱
     */
    @javax.validation.constraints.NotNull(message = "email 电子邮箱 为必填项")
    @Size(max = 255, message = "email 不能大于 255 位")
    @javax.validation.constraints.Email(message = "email不合法，请输入正确的邮箱地址")
    private java.lang.String email;

    /**
     * 联系电话
     */
    @javax.validation.constraints.NotNull(message = "phone 联系电话 为必填项")
    @Size(max = 20, message = "phone 不能大于 20 位")
    private java.lang.String phone;

    /**
     * 在线状态  1-在线 0-离线
     */
    private java.lang.Boolean onlineStatus;

    /**
     * 学生信息
     */
    @Transient
    private Student student;

    /**
     * 教师信息
     */
    @Transient
    private Teacher teacher;

    @JSONField(serialize = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<SysRole> roles = new HashSet<>();

}
