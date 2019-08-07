package cn.edu.gzmu.authserver.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * api
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/7 上午10:19
 */
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final @NonNull HttpServletRequest request;
    private final @NonNull RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final @NonNull FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping;

    @RequestMapping("/")
    public HttpEntity<?> home() {
        Map<RequestMappingInfo, HandlerMethod> endpointHandlerMethods = frameworkEndpointHandlerMapping.getHandlerMethods();
        Map<RequestMappingInfo, HandlerMethod> applicationHandlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        JSONObject result = new JSONObject();
        endpointHandlerMethods.forEach((key, value) ->
                result.put(value.getMethod().getName(), getBaseUrl() + getPath(key)));
        applicationHandlerMethods.forEach((key, value) ->
                result.put(value.getMethod().getName(), getBaseUrl() + getPath(key)));
        return ResponseEntity.ok(result);
    }

    private String getBaseUrl() {
        StringBuffer url = request.getRequestURL();
        if (StringUtils.endsWith(url, "/")) {
            return StringUtils.substringBeforeLast(url.toString(), "/");
        }
        return url.toString();
    }

    private String getPath(RequestMappingInfo requestMappingInfo) {
        return requestMappingInfo.getPatternsCondition()
                .getPatterns()
                .stream()
                .findFirst()
                .orElse("");
    }
}
