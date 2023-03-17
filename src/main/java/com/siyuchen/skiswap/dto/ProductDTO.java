package com.siyuchen.skiswap.dto;

import com.siyuchen.skiswap.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Siyu Chen
 *  A POJO class to handle the product data from frontend to controller.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDTO {
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String shortDescription;
    @NotEmpty
    private String fullDescription;

    private float price;

    private Category category;

    private String productId;

//    private User user;
    @Email
    private String userEmail;
    // TODO: link to the ProductImage

    private String image;


    public String getProductImagePath() {
        if (this.id == null || this.image == null) return "/images/logo.png";
        return "/product-images/" + this.id + "/" + this.image;
    }

}
