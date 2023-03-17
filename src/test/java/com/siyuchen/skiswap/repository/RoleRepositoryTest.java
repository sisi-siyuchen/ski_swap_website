package com.siyuchen.skiswap.repository;


import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole(){
        String roleName = "Admin";
        Role roleAdmin = new Role(roleName);
        roleRepository.save(roleAdmin);
        assertTrue(roleRepository.findRoleByName(roleName) != null);
    }

    @Test
    void testFindRoleByName(){
        String roleName = "ROLE_ADMIN";
        Role actualRole = roleRepository.findRoleByName(roleName);
        assertEquals(roleName, actualRole.getName());
    }

    @Test
    void testFindRoleByUserId(){
        // admin user id is 1
        long admin_id = 1;
        List<Role> rolesForAdmin = roleRepository.findRoleByUserId(1);
        assertEquals("ROLE_ADMIN", rolesForAdmin.get(0).getName());
    }
}