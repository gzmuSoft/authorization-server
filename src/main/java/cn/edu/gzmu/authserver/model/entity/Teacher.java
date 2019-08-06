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
 * @date 19-6-11 下午5:27
 */
@Data
@ToString(callSuper = true)
@Table(name = "teacher")
@Entity(name = "teacher")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Teacher extends BaseEntity implements Serializable {

    /**
     * 用户编号
     */
    private java.lang.Long userId;

    /**
     * 学校编号
     */
    private java.lang.Long schoolId;

    /**
     * 学院编号
     */
    private java.lang.Long collegeId;

    /**
     * 系部编号
     */
    private java.lang.Long depId;

    /**
     * 性别
     */
    @Size(max = 255, message = "gender 不能大于 255 位")
    private java.lang.String gender;

    /**
     * 出生日期
     */
    @javax.validation.constraints.Past
    private java.time.LocalDate birthday;

    /**
     * 民族
     */
    @Size(max = 255, message = "nation 不能大于 255 位")
    private java.lang.String nation;

    /**
     * 学位
     */
    @Size(max = 255, message = "degree 不能大于 255 位")
    private java.lang.String degree;

    /**
     * 最后学历
     */
    @Size(max = 255, message = "academic 不能大于 255 位")
    private java.lang.String academic;

    /**
     * 最后学历毕业时间
     */
    private java.time.LocalDate graduationDate;

    /**
     * 最后学历所学专业
     */
    @Size(max = 255, message = "major 不能大于 255 位")
    private java.lang.String major;

    /**
     * 最后学历毕业院校
     */
    @Size(max = 255, message = "graduateInstitution 不能大于 255 位")
    private java.lang.String graduateInstitution;

    /**
     * 主要研究方向
     */
    @Size(max = 255, message = "majorResearch 不能大于 255 位")
    private java.lang.String majorResearch;

    /**
     * 个人简历
     */
    @Size(max = 2048, message = "resume 不能大于 2048 位")
    private java.lang.String resume;

    /**
     * 参加工作时间
     */
    private java.time.LocalDate workDate;

    /**
     * 职称
     */
    @Size(max = 255, message = "profTitle 不能大于 255 位")
    private java.lang.String profTitle;

    /**
     * 职称评定时间
     */
    private java.time.LocalDate profTitleAssDate;

    /**
     * 是否学术学科带头人
     */
    private java.lang.Byte isAcademicLeader;

    /**
     * 所属学科门类
     */
    @Size(max = 255, message = "subjectCategory 不能大于 255 位")
    private java.lang.String subjectCategory;

    /**
     * 身份证号码
     */
    @Size(max = 18, message = "idNumber 不能大于 18 位")
    private java.lang.String idNumber;

    @Transient
    private SysData school;

    @Transient
    private SysData college;

    @Transient
    private SysData dep;

}
