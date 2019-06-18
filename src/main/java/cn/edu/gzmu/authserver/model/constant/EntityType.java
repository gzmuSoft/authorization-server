package cn.edu.gzmu.authserver.model.constant;

public enum EntityType {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 教师
     */
    TEACHER(2),
    /**
     * 学生
     */
    STUDENT(3);

    private Integer value;

    EntityType(Integer i) {
        value = i;
    }

    public Integer value() {
        return value;
    }


    public static boolean isStudent(Integer id) {
        return STUDENT.value.equals(id);
    }

    public static boolean isTeacher(Integer id) {
        return TEACHER.value.equals(id);
    }

    public static boolean isAdmin(Integer id) {
        return ADMIN.value.equals(id);
    }
}
