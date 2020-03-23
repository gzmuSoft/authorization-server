package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import cn.edu.gzmu.authserver.model.constant.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:26
 */
@Data
@Table(name = "sys_user")
@Entity(name = "sys_user")
@Where(clause = "is_enable = true")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity implements Serializable {

    /**
     * 密码
     */
    @javax.validation.constraints.NotNull(message = "password 密码 为必填项")
    @Size(max = 255, message = "password 不能大于 255 位")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.lang.String password;

    /**
     * 1：正常、2：锁定一小时、3：禁用
     */
    @Enumerated(STRING)
    @javax.validation.constraints.NotNull(message = "status 1：正常、2：锁定一小时、3：禁用 为必填项")
    private UserStatus status;

    /**
     * 图标
     */
    @Size(max = 255, message = "image 不能大于 255 位")
    private java.lang.String image;

    /**
     * 头像
     */
    @Size(max = 255, message = "avatar 不能大于 255 位")
    private java.lang.String avatar;

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

    /**
     * 角色信息
     */
    @Transient
    private Set<SysRole> roles;

}
