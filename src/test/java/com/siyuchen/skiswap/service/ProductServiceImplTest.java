package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
class ProductServiceImplTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Test
    void testFindProductsByUserEmail() {
        Set<Product> products = productService.findProductsByUserEmail("test@test.com");
        assertEquals(2, products.size());
    }
}