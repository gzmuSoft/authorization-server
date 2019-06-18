package cn.edu.gzmu.authserver.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源异常
 *
 * @author echo
 * @date 19-6-18 下午10:18
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResourceException extends RuntimeException {
    private String message;

    public ResourceException() {
        this.message = "资源异常";
    }
}