package cn.edu.gzmu.authserver.model.constant;

/**
 * @author echo
 * @version 1.0
 * @date 19-4-14 16:23
 */
public enum  ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.GRANT_TYPE_SMS;
        }
    },
    EMAIL {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.GRANT_TYPE_EMAIL;
        }
    };
    public abstract String getParamNameOnValidate();
}
