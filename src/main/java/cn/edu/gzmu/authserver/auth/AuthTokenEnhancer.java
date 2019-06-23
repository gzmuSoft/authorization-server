package cn.edu.gzmu.authserver.auth;

import cn.edu.gzmu.authserver.model.constant.EntityType;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.model.entity.SysUser;
import cn.edu.gzmu.authserver.repository.StudentRepository;
import cn.edu.gzmu.authserver.repository.SysUserRepository;
import cn.edu.gzmu.authserver.repository.TeacherRepository;
import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 令牌增强，用于扩展
 *
 * @author echo
 * @date 19-6-19 下午3:53
 */
@Component
@RequiredArgsConstructor
public class AuthTokenEnhancer implements TokenEnhancer {

    private final @NonNull SysUserRepository sysUserRepository;
    private final @NonNull StudentRepository studentRepository;
    private final @NonNull TeacherRepository teacherRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>(2);
        SysUser sysUser = sysUserRepository.findFirstByName(user.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("用户未找到！")
        );
        additionalInfo.put("user_name", user.getUsername());
        additionalInfo.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        additionalInfo.put("user_info", JSON.toJSON(sysUser));
        additionalInfo.put("entity_info", JSON.toJSON(userDetails(sysUser)));
        additionalInfo.put("role_info", JSON.toJSON(sysUser.getRoles()));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    /**
     * 获取用户详细信息
     *
     * @param sysUser 用户
     * @return 结果
     */
    private Object userDetails(SysUser sysUser) {
        if (EntityType.isStudent(sysUser.getEntityType())) {
            return studentRepository.getOne(sysUser.getEntityId());
        } else if (EntityType.isTeacher(sysUser.getEntityType())) {
            return teacherRepository.getOne(sysUser.getEntityId());
        } else if (EntityType.isAdmin(sysUser.getEntityType())){
            return "ROLE_ADMIN";
        } else {
            return null;
        }
    }
}
