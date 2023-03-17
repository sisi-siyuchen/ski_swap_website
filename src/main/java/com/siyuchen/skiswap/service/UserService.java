package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.dto.UserDTO;
import com.siyuchen.skiswap.exception.UserNotFoundException;
import com.siyuchen.skiswap.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
/**
 * @author Siyu Chen
 */
public interface UserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String userName);
    public void create(UserDTO userDTO);
    public User findUserByEmail(String email) throws UserNotFoundException;
    public User findUserByUserName(String userName) throws UserNotFoundException;
    void save(User user);
    void deleteUserById(long id) throws UserNotFoundException;
    void addRoleToUser(User user, String roleName);
    List<User> listAllUsers();
    boolean isEmailUnique(String email);
    boolean isUserNameUnique(String userName);
    void enableOrDisableUserById(long id);
    User findUserByProductId(String productId);

    User findUserById(long id);
}
