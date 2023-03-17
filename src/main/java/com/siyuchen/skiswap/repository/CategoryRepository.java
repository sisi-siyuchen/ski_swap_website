package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Siyu Chen
 *  Repository class to retrieve data from database and map into Category model object.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findCategoryByName(String name);

//    public List<User> findUserByRoleName(String role);

    @Query(value = "SELECT * FROM category WHERE category.id = (SELECT category_id FROM product WHERE id = :id)",
            nativeQuery = true)
    Category findCategoryByProductId(@Param("id") int id);
}
