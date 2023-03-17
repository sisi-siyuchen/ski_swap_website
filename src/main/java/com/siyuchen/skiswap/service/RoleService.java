package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.model.Role;

import java.util.List;
/**
 * @author Siyu Chen
 */
public interface RoleService {
    public void saveRole(Role role);
    public Role findRoleByName(String name);
    public List<Role> getAllRoles();
    public List<Role> getRolesByUserId(long id);
}
