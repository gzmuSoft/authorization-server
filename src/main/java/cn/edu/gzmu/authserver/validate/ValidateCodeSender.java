package cn.edu.gzmu.authserver.validate;

/**
 * @author echo
 * @version 1.0
 * @date 19-4-14 14:14
 */
public interface ValidateCodeSender {

    /**
     * 发送验证码
     *
     * @param receive 接收方
     * @param code   验证码
     */
    void send(String receive, ValidateCode code);
}
