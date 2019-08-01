package cn.edu.gzmu.authserver.model.constant;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum EntityType {
    /**
     * 管理员
     */
    ADMIN("ROLE_ADMIN"),
    /**
     * 教师
     */
    TEACHER("ROLE_TEACHER"),
    /**
     * 学生
     */
    STUDENT("ROLE_STUDENT"),
    /**
     * 系部管理员
     */
    DEPARTMENT_ADMIN("ROLE_DEPARTMENT_ADMIN");

    private String value;

    @Contract(pure = true)
    EntityType(String name) {
        value = name;
    }

    @Contract(pure = true)
    public String value() {
        return value;
    }

    @Contract(pure = true)
    public static boolean isStudent(@NotNull String name) {
        return name.contains(STUDENT.value);
    }

    @Contract(pure = true)
    public static boolean isTeacher(@NotNull String name) {
        return name.contains(TEACHER.value);
    }

    @Contract(pure = true)
    public static boolean isAdmin(@NotNull String name) {
        return name.contains(ADMIN.value);
    }

    @Contract(pure = true)
    public static boolean isDepartmentAdmin(@NotNull String name) {
        return name.contains(DEPARTMENT_ADMIN.value);
    }
}
