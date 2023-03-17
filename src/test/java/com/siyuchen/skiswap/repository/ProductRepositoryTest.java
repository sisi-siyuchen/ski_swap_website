package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.exception.ProductNotFoundException;
import com.siyuchen.skiswap.model.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SkiSwapApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    public void testCreateProduct(){
        Product product = new Product();
        product.setName("Burton Snowboard");
        product.setShortDescription("Used snowboard purchased from Burton in 2019");
        product.setFullDescription("This snowboard is a great gear for new beginners, and only has some minor scratches on it.");
        product.setInStock(true);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());
        product.setPrice(100);
        productRepository.save(product);
        assertTrue(productRepository.findAll().size() > 0);
    }
    @Test
    @Order(2)
    public void testListAllProduct(){
        List<Product> allProduct = productRepository.findAll();
        allProduct.forEach(System.out::println);
    }

    @Test
    @Order(3)
    public void testFindProductById() throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findProductById(1);
        Product product = optionalProduct.orElseThrow(()-> new ProductNotFoundException("Product not exist"));
        assertNotNull(product);
    }
    @Test
    @Order(4)
    void testUpdateProduct(){
        Integer id = 1;
        Product product = productRepository.findProductById(id).get();
        product.setPrice(150);
        productRepository.save(product);
        Product updatedProduct = productRepository.findProductById(id).get();
        assertEquals(150, updatedProduct.getPrice());
    }

    @Test
    @Order(5)
    void testDeleteProduct(){
        Integer id = 1;
        productRepository.deleteById(id);
        assertThrows(ProductNotFoundException.class, ()->{
                productRepository.findProductById(id).orElseThrow(()-> new ProductNotFoundException(""));});
    }

    @Test
    @Order(6)
    void testFindProductByProductId(){
        Product product = new Product();
        String productID = UUID.randomUUID().toString();
        product.setProductId(productID);
        product.setName("Burton Snowboard");
        product.setShortDescription("Used snowboard purchased from Burton in 2019");
        product.setFullDescription("This snowboard is a great gear for new beginners, and only has some minor scratches on it.");
        product.setInStock(true);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());
        product.setPrice(100);
        productRepository.save(product);
        assertEquals(productID, productRepository.findProductByProductId(productID).getProductId());
    }
}