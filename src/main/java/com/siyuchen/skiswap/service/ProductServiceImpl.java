package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.dto.ProductDTO;
import com.siyuchen.skiswap.exception.ProductNotFoundException;
import com.siyuchen.skiswap.model.Product;
import com.siyuchen.skiswap.model.User;
import com.siyuchen.skiswap.repository.ProductRepository;
import com.siyuchen.skiswap.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Siyu Chen
 *  This class is to handle the business logic regarding Product model.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    // set the product based on the availability
    public void updateProductAvailabilityById(int id) throws ProductNotFoundException {
        Product product = findProductById(id);
        product.setInStock(!product.isInStock());
        product.setUpdatedTime(new Date());
        productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Product product = modelMapper.map(productDTO, Product.class);
        User user = userRepository.findUserByEmail(productDTO.getUserEmail());
        product.setUser(user);
        product.setInStock(true);
        product.setProductId(UUID.randomUUID().toString());
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findProductById(int id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findProductById(id);
        Product product = optionalProduct.orElseThrow(()-> new ProductNotFoundException("Product not exist"));
        return product;
    }

    @Override
    public List<ProductDTO> getAllProductByCategory(String categoryName){
        List<Product> products = productRepository.findAll().stream().
                filter(p ->  p.getCategory().toString().equals(categoryName)).collect(Collectors.toList());

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products){
            ProductDTO productDTO= getProductDTOByProduct(product);
            User contactUser = userRepository.findUserByProductId(product.getProductId());
            productDTO.setUserEmail(contactUser.getEmail());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductDTO getProductDTOByProduct(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

    @Override
    public void updateProductInfo(ProductDTO productDTO) {
        Product product = productRepository.findProductByProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setShortDescription(productDTO.getShortDescription());
        product.setPrice(productDTO.getPrice());
        product.setFullDescription(productDTO.getFullDescription());
        product.setCategory(productDTO.getCategory());
        product.setImage(productDTO.getImage());
        product.setUpdatedTime(new Date());
        productRepository.save(product);
    }


    private Set<Product> findProductsByUser(User user) {
        Set<Product> userProducts = new HashSet<>();
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts){
            if (product.getUser() != null && product.getUser().getId() == user.getId()){
                userProducts.add(product);
            }
        }
        return userProducts;
    }

    @Override
    public Set<Product> findProductsByUserEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        return findProductsByUser(user);
    }

    @Override
    public Product findProductByProductId(String productID) {
        return productRepository.findProductByProductId(productID);
    }

}
