package com.siyuchen.skiswap;

import com.siyuchen.skiswap.exception.CategoryNotFoundException;
import com.siyuchen.skiswap.exception.UserNotFoundException;
import com.siyuchen.skiswap.model.Category;
import com.siyuchen.skiswap.model.Product;
import com.siyuchen.skiswap.model.Role;
import com.siyuchen.skiswap.model.User;
import com.siyuchen.skiswap.repository.ProductRepository;
import com.siyuchen.skiswap.service.CategoryService;
import com.siyuchen.skiswap.service.RoleService;
import com.siyuchen.skiswap.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * 	@author Siyu Chen
 *  Entry point to the website application.
 *  Initialized the roles, categories, products, one ADMIN and one USER.
 */
@SpringBootApplication
@Slf4j
public class SkiSwapApplication implements CommandLineRunner {
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(SkiSwapApplication.class, args);
		log.info("Ski Swap Program started");
	}

	/**
	 * This method is to initialize the roles, categories, users and products.
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		initRoles();
		initCategories();
		initUsers();
		initProducts();

	}

	/**
	 * Initialize roles in database.
	 */
	private void initRoles()
	{
		roleService.saveRole(new Role("ROLE_ADMIN"));
		roleService.saveRole(new Role("ROLE_USER"));
	}

	/**
	 * Initialize categories in database.
	 */
	private void initCategories()
	{
		categoryService.saveCategory(new Category("SKI"));
		categoryService.saveCategory(new Category("SNOWBOARD"));
		categoryService.saveCategory(new Category("ESSENTIAL"));
	}

	/**
	 * Initialize users in database.
	 */
	private void initUsers() throws UserNotFoundException {
		//Login 'admin@admin.com'
		//Password 'test'
		userService.save(new User("admin", UUID.randomUUID().toString(),"admin@admin.com",
				 "$2a$11$DZfZLO720bZby.1QWCu81.gg2BUYCJC7PSsjEUMho.ZaVUVC1h9ZC"));

		User user= userService.findUserByEmail("admin@admin.com");
		user.setRoles(Arrays.asList(roleService.findRoleByName("ROLE_ADMIN")));
		user.setEnabled(true);
		userService.save(user);

		userService.save(new User("test", UUID.randomUUID().toString(),"test@test.com",
				"$2a$11$DZfZLO720bZby.1QWCu81.gg2BUYCJC7PSsjEUMho.ZaVUVC1h9ZC"));

		User user1= userService.findUserByEmail("test@test.com");
		user1.setRoles(Arrays.asList(roleService.findRoleByName("ROLE_USER")));
		user1.setEnabled(true);
		userService.save(user1);
	}

	/**
	 * To initialize the products associated with users and categories.
	 * @throws CategoryNotFoundException
	 */
	private void initProducts() throws CategoryNotFoundException {

		try{
			User user1= userService.findUserByEmail("test@test.com");
			User user = userService.findUserByEmail("admin@admin.com");
			Category skiCategory = categoryService.findCategoryByName("SKI");
			Category snowboardCategory = categoryService.findCategoryByName("SNOWBOARD");
			Category essentialCategory = categoryService.findCategoryByName("ESSENTIAL");
			Product product1 = new Product();
			product1.setName("Burton Snowboard");
			product1.setShortDescription("Used snowboard purchased from Burton in 2019");
			product1.setFullDescription("This snowboard is a great gear for new beginners, and only has some minor scratches on it.");
			product1.setInStock(true);
			product1.setProductId(UUID.randomUUID().toString());
			product1.setCreatedTime(new Date());
			product1.setUpdatedTime(new Date());
			product1.setPrice(100);
			product1.setCategory(snowboardCategory);
			product1.setUser(user1);
			productRepository.save(product1);
			Product product2 = new Product();
			product2.setName("Atomic Ski");
			product2.setShortDescription("Used ski purchased from Atomic in 2021");
			product2.setFullDescription("This ski is a great gear for new beginners, and only has a few minor scratches on it.");
			product2.setInStock(true);
			product2.setProductId(UUID.randomUUID().toString());
			product2.setCreatedTime(new Date());
			product2.setUpdatedTime(new Date());
			product2.setPrice(120);
			product2.setCategory(skiCategory);
			product2.setUser(user1);
			productRepository.save(product2);
			Product product3 = new Product();
			product3.setName("Atomic Ski");
			product3.setShortDescription("Used ski purchased from Atomic in 2019");
			product3.setFullDescription("This ski is a great gear for new beginners, and only has a few minor scratches on it.");
			product3.setInStock(true);
			product3.setProductId(UUID.randomUUID().toString());
			product3.setCreatedTime(new Date());
			product3.setUpdatedTime(new Date());
			product3.setPrice(80);
			product3.setCategory(categoryService.findCategoryByName("SKI"));
			product3.setUser(user1);
			productRepository.save(product3);
			Product product4 = new Product();
			product4.setName("Ski");
			product4.setShortDescription("Used ski purchased from LevelNine in 2018");
			product4.setFullDescription("This ski is a great gear for new beginners, and only has a few minor scratches on it.");
			product4.setInStock(true);
			product4.setProductId(UUID.randomUUID().toString());
			product4.setCreatedTime(new Date());
			product4.setUpdatedTime(new Date());
			product4.setPrice(60);
			product4.setCategory(skiCategory);
			product4.setUser(user);
			productRepository.save(product4);
			Product product5 = new Product();
			product5.setName("Rossignol Ski");
			product5.setShortDescription("Used ski purchased from evo in 2018");
			product5.setFullDescription("This ski is a great gear for new beginners, and only has a few minor scratches on it.");
			product5.setInStock(true);
			product5.setProductId(UUID.randomUUID().toString());
			product5.setCreatedTime(new Date());
			product5.setUpdatedTime(new Date());
			product5.setPrice(70);
			product5.setCategory(skiCategory);
			product5.setUser(user1);
			productRepository.save(product5);
		} catch (Exception e){
			System.out.println(e);
		}

//		Product product6 = new Product();
//		product6.setName("Snow Jacket");
//		product6.setShortDescription("Used snow jacket purchased from backcountry in 2021");
//		product6.setFullDescription("Perfect snow jacket for powder day. Nearly new condition.");
//		product6.setInStock(true);
//		product6.setCreatedTime(new Date());
//		product6.setUpdatedTime(new Date());
//		product6.setPrice(40);
//		product6.setCategory(Category.ESSENTIAL);
//		productRepository.save(product6);
	}


}
