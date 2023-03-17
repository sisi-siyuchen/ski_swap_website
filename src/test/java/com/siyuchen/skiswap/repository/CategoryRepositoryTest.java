package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.Category;
import com.siyuchen.skiswap.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @Order(1)
    void saveCategory(){
        Category category = new Category("SKKKKIII");
        categoryRepository.save(category);
        assertTrue(categoryRepository.findAll().size()>0);
    }

    @Test
    @Order(2)
    void findCategoryByName() {
        Category category = categoryRepository.findCategoryByName("SKKKKIII").get();
        assertNotNull(category);
    }

    @Test
    @Order(3)
    void findCategoryByProductId() {
        Product product6 = new Product();
		product6.setName("Snow Jacket");
		product6.setShortDescription("Used snow jacket purchased from backcountry in 2021");
		product6.setFullDescription("Perfect snow jacket for powder day. Nearly new condition.");
		product6.setInStock(true);
		product6.setCreatedTime(new Date());
		product6.setUpdatedTime(new Date());
		product6.setPrice(40);
		product6.setCategory(categoryRepository.findCategoryByName("SKKKKIII").get());
		productRepository.save(product6);
        Assertions.assertTrue(categoryRepository.findCategoryByProductId(product6.getId()).toString().equals("SKKKKIII"));
    }


}