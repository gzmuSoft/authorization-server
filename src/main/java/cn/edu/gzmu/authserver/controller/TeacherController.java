package cn.edu.gzmu.authserver.controller;

import cn.edu.gzmu.authserver.model.constant.AuthConstant;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.model.entity.Teacher;
import cn.edu.gzmu.authserver.model.exception.ResourceException;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/8/4 下午8:49
 */
@RepositoryRestController
@RequiredArgsConstructor
@RequestMapping(AuthConstant.SEMESTER)
public class TeacherController {

    private final SysUserRepository sysUserRepository;
    private final TeacherRepository teacherRepository;
    private final static String AVATAR = "avatar";
    private final static String IMAGE = "image";
    private final static String PHONE = "phone";
    private final static String EMAIL = "email";
    private final static String NAME = "name";
    private final static String ID = "id";

    @GetMapping("/id/{id}")
    public HttpEntity<?> userId(@PathVariable Long id) {
        final SysUser user = sysUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("找不到当前用户信息"));
        final Teacher teacher = teacherRepository.findFirstByUserId(id)
                .orElseThrow(() -> new UsernameNotFoundException("找不到当前用户的教师信息"));
        final JSONObject result = new JSONObject();
        result.put(NAME, teacher.getName());
        result.put(IMAGE, user.getImage());
        result.put(AVATAR, user.getAvatar());
        result.put(PHONE, user.getPhone());
        result.put(EMAIL, user.getEmail());
        return ok(result);
    }

    @GetMapping("/ids")
    public HttpEntity<?> userIds(@RequestParam List<Long> ids) {
        final List<JSONObject> users = sysUserRepository.findAllByIdIn(ids)
                .stream().sorted(Comparator.comparingLong(SysUser::getId))
                .map(u -> {
                    final JSONObject user = new JSONObject();
                    user.put(ID, u.getId());
                    user.put(IMAGE, u.getImage());
                    user.put(AVATAR, u.getAvatar());
                    user.put(PHONE, u.getPhone());
                    user.put(EMAIL, u.getEmail());
                    return user;
                }).collect(Collectors.toList());
        final List<JSONObject> teachers = teacherRepository.findAllByUserIdIn(ids)
                .stream().sorted(Comparator.comparingLong(Teacher::getUserId))
                .map(t -> {
                    final JSONObject teacher = new JSONObject();
                    teacher.put(ID, t.getUserId());
                    teacher.put(NAME, t.getName());
                    return teacher;
                }).collect(Collectors.toList());
        if (users.size() != teachers.size()) {
            throw new ResourceException("资源错误");
        }
        final List<JSONObject> result = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            final JSONObject user = users.get(i);
            final JSONObject teacher = teachers.get(i);
            final JSONObject r = new JSONObject();
            r.put(ID, user.getLong(ID));
            r.put(IMAGE, user.getString(IMAGE));
            r.put(AVATAR, user.getString(AVATAR));
            r.put(PHONE, user.getString(PHONE));
            r.put(EMAIL, user.getString(EMAIL));
            r.put(NAME, teacher.getString(NAME));
            result.add(r);
        }
        return ok(result);
    }

}