package cn.edu.gzmu.authserver.base;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author echo
 * @version 1.0.0
 * @date 19-6-11 下午5:24
 */
@Getter
@Setter
@MappedSuperclass
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    /**
     * id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private java.lang.Long id;

    /**
     * 名称
     */
    @Size(max = 30, message = "name 长度不能超过 30")
    private String name;

    /**
     * 全称
     */
    @Size(max = 55, message = "spell 长度不能超过 55")
    private String spell;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false, columnDefinition = "datetime not null default now() comment '创建时间'")
    private LocalDateTime createTime;

    /**
     * 创建用户
     */
    @CreatedBy
    @Column(name = "create_user")
    private String createUser;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "modify_time", nullable = false, columnDefinition = "datetime not null default now() comment '修改时间'")
    private LocalDateTime modifyTime;

    /**
     * 修改用户
     */
    @LastModifiedBy
    @Column(name = "modify_user")
    private String modifyUser;

    /**
     * 备注
     */
    @Size(max = 255, message = "remark 长度不能超过 255")
    private String remark;

    /**
     * 是否启用
     */
    private Boolean isEnable = true;

}
