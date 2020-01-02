package cn.edu.gzmu.authserver.model.constant;

/**
 * 用户状态.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午9:16
 */
public enum UserStatus {
    /**
     * 正常
     */
    NORMAL,
    /**
     * 锁定
     */
    LOCKED,
    /**
     * 是否启用
     */
    ENABLE;

    /**
     * 是否正常
     *
     * @param status 状态
     * @return 结果
     */
    public static Boolean isNormal(UserStatus status) {
        return UserStatus.NORMAL.equals(status);
    }

    /**
     * 是否锁定
     *
     * @param status 状态
     * @return 结果
     */
    public static Boolean isLocked(UserStatus status) {
        return UserStatus.LOCKED.equals(status);
    }

    /**
     * 是否启用
     *
     * @param status 状态
     * @return 结果
     */
    public static Boolean isEnable(UserStatus status) {
        return UserStatus.ENABLE.equals(status);
    }
}
