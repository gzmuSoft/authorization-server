package cn.edu.gzmu.authserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping;

    @Test
    void contextLoads() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods1 = frameworkEndpointHandlerMapping.getHandlerMethods();
        handlerMethods1.forEach((requestMappingInfo, handlerMethod) -> System.out.println(requestMappingInfo.toString() + handlerMethod));
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((requestMappingInfo, handlerMethod) -> System.out.println(requestMappingInfo.toString() + handlerMethod));
    }

}
