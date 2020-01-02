package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.repository.SysRoleRepository;
import cn.edu.gzmu.authserver.service.SysRoleService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/1 下午10:22
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {
    private @NonNull SysRoleRepository sysRoleRepository;

    @Override
    public Set<SysRole> findAllByRoles(Set<SysRole> roles) {
        List<@NotNull Long> ids = roles.stream()
                .filter(role -> role.getParentId() != 0)
                .map(SysRole::getParentId)
                .collect(Collectors.toList());
        if (roles.size() == 0) {
            return roles;
        }
        roles.addAll(sysRoleRepository.findByIdIn(ids));
        return roles;
    }

}
