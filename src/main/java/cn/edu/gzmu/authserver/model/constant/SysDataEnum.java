package cn.edu.gzmu.authserver.model.constant;

import javax.validation.constraints.NotNull;

/**
 * 数据类型枚举
 *
 * @author echo
 * @version 1.0
 * @date 19-5-21 20:46
 */
public enum SysDataEnum {
    /**
     * 学校
     */
    SCHOOL(1),
    /**
     * 学院
     */
    COLLEGE(2),
    /**
     * 系部
     */
    DEPARTMENTS(3),
    /**
     * 专业
     */
    PROFESSION(4),
    /**
     * 班级
     */
    CLASSES(5),
    /**
     * 学历
     */
    EDUCATION(6),
    /**
     * 学位
     */
    DEGREE(7),
    /**
     * 教师毕业专业
     */
    TEACHER_GRADUATION_MAJOR(8),
    /**
     * 民族
     */
    NATION(9),
    /**
     * 研究方向
     */
    RESEARCH_DIRECTION(10),
    /**
     * 职称
     */
    JOB_TITLE(11);

    private int type;

    SysDataEnum(int type) {
        this.type = type;
    }

    public static boolean match(@NotNull SysDataEnum sysDataEnum, @NotNull int type) {
        return sysDataEnum.getType() == type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean match(int type) {
        return match(this, type);
    }
}
