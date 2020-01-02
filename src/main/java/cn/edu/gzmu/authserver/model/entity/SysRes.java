package cn.edu.gzmu.authserver.model.entity;

import cn.edu.gzmu.authserver.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:27
 */
@Data
@Table(name = "sys_res")
@Entity(name = "sys_res")
@Where(clause = "is_enable = true")
@ToString(callSuper = true, exclude = "roles")
@EqualsAndHashCode(callSuper = true, exclude = "roles")
@Accessors(chain = true)
public class SysRes extends BaseEntity implements Serializable {

    /**
     * 路径
     */
    @Size(max = 55, message = "url 不能大于 55 位")
    private java.lang.String url;

    /**
     * 描述
     */
    @Size(max = 55, message = "describe 不能大于 55 位")
    private java.lang.String describe;

    /**
     * 方法
     */
    @Size(max = 55, message = "method 不能大于 55 位")
    private java.lang.String method;

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "res", fetch = FetchType.EAGER)
    private Set<SysRole> roles = new HashSet<>();
}
