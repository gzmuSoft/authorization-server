package cn.edu.gzmu.authserver.service.impl;

import cn.edu.gzmu.authserver.base.BaseEntity;
import cn.edu.gzmu.authserver.model.entity.SysRole;
import cn.edu.gzmu.authserver.repository.SysRoleRepository;
import cn.edu.gzmu.authserver.service.SysRoleService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
        List<Long> roleIds = roles.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        roles.addAll(sysRoleRepository.searchAllRoleByIds(roleIds));
        return roles;
    }

    @Override
    public Set<SysRole> findAllByUser(Long userId) {
        Set<SysRole> sysRoles = sysRoleRepository.searchAllByUserId(userId);
        List<Long> roleIds = sysRoles.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        sysRoles.addAll(sysRoleRepository.searchAllRoleByIds(roleIds));
        return sysRoles;
    }

    @Override
    public Set<SysRole> findAllByRes(Long resId) {
        return sysRoleRepository.searchAllByResId(resId);
    }

}
