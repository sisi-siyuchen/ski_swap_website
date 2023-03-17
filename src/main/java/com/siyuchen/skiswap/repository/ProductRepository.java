package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @author Siyu Chen
 *  Repository class to retrieve data from database and map into Product model object.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductById(Integer id);

    Product findProductByProductId(String productId);

}
