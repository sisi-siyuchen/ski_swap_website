package com.siyuchen.skiswap.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Siyu Chen
 *  Entity for storing category in database
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public Category(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
}