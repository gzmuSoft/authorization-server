package cn.edu.gzmu.authserver.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午10:14
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public HttpEntity<?> handler(AccessDeniedException exception) {
        JSONObject param = new JSONObject();
        param.put("error", exception.getClass().getSimpleName());
        param.put("error_description", exception.getLocalizedMessage());
        return new ResponseEntity<>(param, HttpStatus.UNAUTHORIZED);
    }

}
