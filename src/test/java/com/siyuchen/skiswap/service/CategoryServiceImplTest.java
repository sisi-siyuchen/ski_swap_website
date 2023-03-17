package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
class CategoryServiceImplTest {
    @Autowired
    CategoryService categoryService;

    @Test
    void getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        assertEquals(3, categories.size());
    }
}