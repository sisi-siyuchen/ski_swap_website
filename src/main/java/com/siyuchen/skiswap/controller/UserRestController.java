package com.siyuchen.skiswap.controller;

import com.siyuchen.skiswap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siyu Chen
 * This class is only for handling the frontend sign up validation.
 * RestController here is only for sending the JSON data back to front end.
 */
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    /**
     * For the signup page, after user submit the form, this method check if the email and userName already
     * exist in the database or not, and send the response back to front end with informative messages.
     * @param email
     * @param userName
     * @return
     */
    @PostMapping("/signup/check_email")
    public String checkDuplicateEmail(@RequestParam("email") String email, @RequestParam("name") String userName){
//        System.out.println(email);
//        System.out.println(userName);

        if (!userService.isEmailUnique(email) && !userService.isUserNameUnique(userName)){
            return "username and email exist";
        } else if (!userService.isUserNameUnique(userName)){
            return "username exists";
        } else if (!userService.isEmailUnique(email)){
            return "email exists";
        } else {
            return "valid";
        }
    }
}
