package com.siyuchen.skiswap.service;

import com.siyuchen.skiswap.dto.UserDTO;
import com.siyuchen.skiswap.exception.UserNotFoundException;
import com.siyuchen.skiswap.model.Role;
import com.siyuchen.skiswap.model.User;
import com.siyuchen.skiswap.repository.ProductRepository;
import com.siyuchen.skiswap.repository.UserRepository;
import com.siyuchen.skiswap.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Siyu Chen
 *  This class is to handle the business logic regarding User model.
 *  This service class also implements UserDetailsSerivce.
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * This method is to override the loadUserByUsername from UserDetailsSerivce.
     * @param email
     * @return new UserDetailsImpl
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            log.warn("Invalid email {}", email);
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new UserDetailsImpl(user, roleService.getRolesByUserId(user.getId()));
    }

    /**
     * Using model mapper to map userDTO into User object, and persistence the data
     * @param userDTO
     */
    @Override
    public void create(UserDTO userDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(userDTO, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleService.findRoleByName("ROLE_USER")));
        userRepository.save(user);
    }

    /**
     * Find the user from database by given email.
     * @param email
     * @return User
     * @throws UserNotFoundException
     */
    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new UserNotFoundException("Could not find any user with email: "+email);
        } else {
            return user;
        }
    }

    /**
     * Find the user from database by given userName.
     * @param userName
     * @return User
     * @throws UserNotFoundException
     */
    @Override
    public User findUserByUserName(String userName) throws UserNotFoundException{
        User user = userRepository.findUserByUserName(userName);
        if (user == null){
            throw new UserNotFoundException("Could not find any user with username: " + userName);
        } else {
            return user;
        }
    }

    /**
     * Save the user into database
     * @param user
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Delete user in the database given user ID.
     * @param id
     * @throws UserNotFoundException
     */
    @Override
    public void deleteUserById(long id) throws UserNotFoundException{
        Long count = userRepository.countUserById(id);
        // sanity check
        if (count == null || count == 0){
            throw new UserNotFoundException("Could not find any user with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * This method is to assign another role (based on the roleName) to the User.
     * @param user
     * @param roleName
     */
    @Override
    public void addRoleToUser(User user, String roleName) {
         Role role = roleService.findRoleByName(roleName);
         user.addRole(role);
         userRepository.save(user);
    }

    /**
     * This method find all users from user database.
     * @return List<User>
     */
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This method is to check whether given email exists in database or not.
     * @param email
     * @return boolean
     */
    @Override
    public boolean isEmailUnique(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

    /**
     * This method is to check whether given userName exists in databse or not.
     * @param userName
     * @return boolean
     */
    @Override
    public boolean isUserNameUnique(String userName) {
        return userRepository.findUserByUserName(userName) == null;
    }

    /**
     * This method is to switch enable status for user given user ID.
     * @param id
     */
    @Override
    public void enableOrDisableUserById(long id) {
        User user = userRepository.findUserById(id);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    /**
     * This method is to find user that is associated with given ProductID(UUID).
     * @param productId
     * @return
     */
    @Override
    public User findUserByProductId(String productId) {
        return userRepository.findUserByProductId(productId);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findUserById(id);
    }


}
