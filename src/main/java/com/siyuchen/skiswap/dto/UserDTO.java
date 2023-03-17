package com.siyuchen.skiswap.dto;


import com.siyuchen.skiswap.validation.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author Siyu Chen
 * A POJO class to handle the user data from frontend to controller.
 */
@Getter
@Setter
@NoArgsConstructor
@FieldMatch.List( { @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")})
public class UserDTO {

    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String userName;
    @NotEmpty(message = "Required")
    private String password;

    @NotEmpty(message = "Required")
    private String matchingPassword;

    public UserDTO(@NotEmpty String userName, @Email String email, @NotEmpty(message = "Required") String password, @NotEmpty(message = "Required") String matchingPassword) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }
}
