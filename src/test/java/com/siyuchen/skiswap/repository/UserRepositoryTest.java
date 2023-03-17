package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.SkiSwapApplication;
import com.siyuchen.skiswap.model.User;
import com.siyuchen.skiswap.model.Product;
import com.siyuchen.skiswap.model.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SkiSwapApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    public void createUser(){
        User user = new User("funTest", UUID.randomUUID().toString(), "test@funtest.com", "test");
        Role roleAdmin = roleRepository.findRoleByName("ROLE_ADMIN");
        user.setRoles(Arrays.asList(roleAdmin));
        userRepository.save(user);
    }
    @Test
    @Order(2)
    public void testFindUserByName(){
        assertNotNull(userRepository.findUserByUserName("funTest"));
    }
    @Test
    @Order(3)
    public void testFindUserByEmail(){
        String email = "test@funtest.com";
        assertEquals(email, userRepository.findUserByEmail(email).getEmail());
    }
    @Test
    @Order(4)
    public void testAddRoleToUser(){
        String newRoleName = "ROLE_USER";
        User user = userRepository.findUserByUserName("funTest");
        Role newRole = roleRepository.findRoleByName("ROLE_USER");
        user.addRole(newRole);
        userRepository.save(user);
        // check the result in mySQL database
    }
    @Test
    @Order(5)
    void testListAllUsers(){
        List<User> userList = userRepository.findAll();
//        userList.forEach(user -> System.out.println(user));
        assertEquals(userList.size(), 3);
    }
    @Test
    @Order(6)
    void testEnableUserById(){
        User user = userRepository.findUserById(3);
        assertTrue(!user.isEnabled());
        user.setEnabled(!user.isEnabled());
        // user.setEmail("update@test.com");
        userRepository.save(user);
        User updatedUser = userRepository.findUserByUserName("funTest");
//        System.out.println(updatedUser);
        assertTrue(updatedUser.isEnabled());
        // assertEquals("update@test.com", updatedUser.getEmail());
    }
    @Test
    @Order(8)
    void testDeleteUSer(){
        User user = userRepository.findUserByUserName("funTest");
        userRepository.delete(user);
        assertNull(userRepository.findUserByUserName("funTest"));
    }

    @Test
    @Order(7)
    void testCountUserById(){
        assertEquals(1, userRepository.countUserById(1));
    }

    @Test
    @Order(9)
    void findUserByProductId(){
        User user = userRepository.findUserByUserName("test");
        String productId = UUID.randomUUID().toString();
        Product product = new Product();
        product.setName("lol");
        product.setPrice(0);
        product.setProductId(productId);
        product.setFullDescription("lmao");
        product.setUser(user);
        productRepository.save(product);
        User actualUser = userRepository.findUserByProductId(productId);
        assertEquals("test", actualUser.getUserName());
    }
    @Test
    @Order(10)
    void testFindUserById(){
        // user id = 1, name should be admin
        long id = 1;
        assertEquals("admin", userRepository.findUserById(id).getUserName());
    }


}