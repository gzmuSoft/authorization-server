package cn.edu.gzmu.authserver.model.exception;

/**
 * 资源已存在异常
 *
 * @author echo
 * @version 1.0
 * @date 2019-5-21 10:43:57
 */
public class ResourceExistException extends RuntimeException {

    public ResourceExistException(){
        super("资源已存在！");
    }

    public ResourceExistException(String message) {
        super(message);
    }

    public ResourceExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

