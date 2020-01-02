package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午8:49
 */
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(AuthConstant.STUDENT)
public class StudentController {

}