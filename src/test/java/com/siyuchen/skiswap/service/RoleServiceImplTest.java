package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
class RoleServiceImplTest {
    @Autowired
    RoleService roleService;

    @Test
    void getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        assertEquals(2, roles.size());
    }
}