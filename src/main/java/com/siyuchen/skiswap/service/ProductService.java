package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.dto.ProductDTO;
import com.siyuchen.skiswap.exception.ProductNotFoundException;
import com.siyuchen.skiswap.model.Product;

import java.util.List;
import java.util.Set;
/**
 * @author Siyu Chen
 */
public interface ProductService {
    void updateProductAvailabilityById(int id) throws ProductNotFoundException;
    Product createProduct(ProductDTO productDTO);
    void deleteProductById(int id);
    Product findProductById(int id) throws ProductNotFoundException;
    List<ProductDTO> getAllProductByCategory(String categoryName);

    List<Product> getAllProducts();

    ProductDTO getProductDTOByProduct(Product product);

    void updateProductInfo(ProductDTO productDTO);

    Set<Product> findProductsByUserEmail(String userEmail);
    Product findProductByProductId(String productID);
}
