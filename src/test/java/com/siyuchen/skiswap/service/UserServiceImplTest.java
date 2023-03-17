package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.SkiSwapApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @ParameterizedTest
    @CsvSource({"test@admin.com, true", "admin@admin.com, false"})
    void isEmailUnique(String email, boolean flag) {
        if (flag){
            assertTrue(userService.isEmailUnique(email));
        } else {
            assertFalse(userService.isEmailUnique(email));
        }

    }
}