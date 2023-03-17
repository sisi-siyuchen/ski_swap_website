package com.siyuchen.skiswap.repository;

import com.siyuchen.skiswap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Siyu Chen
 * Repository class to retrieve data from database and map into Role model object.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleByName(String role);

//    public List<User> findUserByRoleName(String role);

    @Query(value = "SELECT * FROM role WHERE role.id = (SELECT role_id FROM users_roles WHERE user_id = :id)",
    nativeQuery = true)
    public List<Role> findRoleByUserId(@Param("id") long id);
}
