package com.siyuchen.skiswap.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author Siyu Chen
 *  Entity for storing Roles in database
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 40, nullable = false, unique = true)
    private String name;

//    @Fetch(FetchMode.JOIN)
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
    public Role(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}
