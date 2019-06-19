package cn.edu.gzmu.authserver.validate;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author echo
 * @version 1.0
 * @date 19-4-14 11:23
 */
@Data
public class ValidateCode implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 有效期
     */
    private int expireIn;
    /**
     * 构造
     */
    public ValidateCode() {
    }

    /**
     * 几秒后过期
     *
     * @param code     验证码
     * @param expireIn 过期时间，单位/秒
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
        this.expireIn = expireIn;
    }

    /**
     * 构造
     *
     * @param code       验证码
     * @param expireTime 过期时间
     */
    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 是否已经过期
     *
     * @return 结果
     */
    public boolean expired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
