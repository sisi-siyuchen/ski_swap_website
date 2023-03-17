package com.siyuchen.skiswap.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 * @author Siyu Chen
 *  Entity for storing product in database
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String shortDescription;
    @Column(length = 4096, nullable = false)
    private String fullDescription;
    private boolean inStock;
    private Date createdTime;
    private Date updatedTime;
    private float price;
    private String productId;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString(){
        return ("id: " + id + ", name: " + name + ", productId: " + productId + ", price: " +
                price + ", shortDescription: " + shortDescription + ".");
    }

    @Transient
    public String getProductImagePath() {
        if (this.id == null || this.image == null) return "/images/logo.png";
        return "/product-images/" + this.id + "/" + this.image;
    }

}
