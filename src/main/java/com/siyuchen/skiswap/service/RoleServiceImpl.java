package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.model.Role;
import com.siyuchen.skiswap.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Siyu Chen
 *  This class is to handle the business logic regarding Role model.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesByUserId(long id) {
        return roleRepository.findRoleByUserId(id);
    }
}
