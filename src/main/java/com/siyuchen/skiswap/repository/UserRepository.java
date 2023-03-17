package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Siyu Chen
 * Repository class to retrieve data from database and map into user model object.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserByUserName(String userName);
    @Query(value = "SELECT COUNT(*) FROM user WHERE user.id= :id", nativeQuery = true)
    Long countUserById(@Param("id") long id);

//    // This update didn't work.
//    @Modifying
//    @Query(value = "UPDATE user SET user.enabled = :enabled WHERE user.id = :id", nativeQuery = true)
//    public void updateEnabledStatus(@Param("id") long id, @Param("enabled")boolean enabled);

    User findUserById(long id);

    @Query(value = "SELECT * FROM user WHERE user.id = (SELECT user_id FROM product WHERE product_id = :id)",
            nativeQuery = true)
    User findUserByProductId(@Param("id") String productId);

}

