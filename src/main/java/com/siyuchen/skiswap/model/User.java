package com.siyuchen.skiswap.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;
/**
 * @author Siyu Chen
 *  Entity for storing User in database
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 128, nullable = false, unique = true)
    private String userName;
    private String userId;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String password;

    // Those fields below are just optional for signup and login function
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public User(String userName, String userId, String email, String password){
        this.userName = userName;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    /**
     * This is to enable adding roles for users.
     * @param role
     */
    public void addRole(Role role){
        this.roles.add(role);
    }

    @Override
    public String toString(){
        return ("id: " + id + ", userName: " + userName + ", email: " + email + ".");
    }
}
