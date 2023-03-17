package com.siyuchen.skiswap.controller;

import com.siyuchen.skiswap.dto.UserDTO;
import com.siyuchen.skiswap.exception.UserNotFoundException;
import com.siyuchen.skiswap.model.EmailDetails;
import com.siyuchen.skiswap.model.User;
import com.siyuchen.skiswap.service.EmailService;
import com.siyuchen.skiswap.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Siyu Chen
 * This class is to handle the user-related request from the frontend.
 */
@Controller
@Slf4j
public class UserController {

    /**
     * Trim the values coming as part of request.
     * if only blank spaces are present, value will be NULL.
     * @param dataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @Autowired
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * For the request to load the landing page for this website
     * @return
     */
    @GetMapping("/")
    private String loadLandingPage(){
        log.info("Landing page displayed");
        return "index";
    }

    /**
     * For the request of signup from the front end. Create a userDTO and add it to model.
     * @param model
     * @return
     */
    @GetMapping("/signup")
    public String signUp(Model model){
        // the data from the empty form will be bind with this new userDTO
        model.addAttribute("userDto", new UserDTO());
        return "signup";
    }

    /**
     * For processing the signup request
     * @param userDTO
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/signupProcess")
    public String signupProcess(@Valid @ModelAttribute ("userDto") UserDTO userDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.warn("Wrong attempt");
            return "signup";
        }
        userService.create(userDTO);
//        redirectAttributes.addFlashAttribute("message", "Congrats! You have registered successfully");
        return "confirmation";
    }

    /**
     *  For the request of login from the front end. Login logic is handled by Spring Security.
     * @return
     */
    @RequestMapping("/login")
    public String getLoginPage()
    {
        log.info("Login page displayed");
        return "login";
    }

    /**
     * For display the home page for users after they log in successfully.
     * @return
     */
    @RequestMapping("/home")
    public String getHome()
    {
        return "home";
    }

    /**
     * For handling user's request to load Size Calculator page.
     * @return
     */
    @RequestMapping("/skiCalculator")
    public String getSkiCalculator()
    {
        return "ski_calculator";
    }

//    /**
//     * For testing purpose, only add the user as an admin now
//     * Need to modify later
//     */
//    @RequestMapping("/addAsSeller")
//    public String addAsSeller(Authentication authentication, RedirectAttributes redirectAttributes){
//        String userEmail = authentication.getName();
//        System.out.println(userEmail);
//        try{
//            User user = userService.findUserByEmail(userEmail);
//            userService.addRoleToUser(user, "ROLE_ADMIN");
//            redirectAttributes.addFlashAttribute("message", "Congrats! You have registered as a Seller!");
//        } catch(Exception e){
//            e.printStackTrace();
//        } finally {
//            return "redirect:/home";
//        }
//    }

    /**
     * For admin to retrieve all users' information on the front end.
     * @param model
     * @return
     */
    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> users = userService.listAllUsers();
        users.forEach(user -> System.out.println(user));
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * For the request from ADMIN to delete selected user.
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("message", "The user ID " + id
                                + " has been deleted successfully");
        } catch (UserNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    /**
     * This is for the ADMIN management of whether enable a user or not. User has to be enabled before log in.
     * @param id
     * @return
     */
    @RequestMapping("/users/status/{id}")
    public String enableOrDisableUserById(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        // check if current user id is enabled or not
        User curUser = userService.findUserById(id);
        boolean userWasActivated = curUser.isEnabled();
        userService.enableOrDisableUserById(id);
        boolean userIsActivated = curUser.isEnabled();
        // if the user was not activated and is activated now, send an email notification
        if (!userWasActivated && userIsActivated){
            EmailDetails details = new EmailDetails();
            details.setSubject("Congrats! Your account at Ski Swap has been activated!");
            details.setMsgBody("Thank you for registering at Ski Swap. This email is to notify you that " +
                    "your account at Ski Swap has been approved by the administrator. Feel free to sign in" +
                    " and explore around. Welcome to the community!");
            details.setRecipient(curUser.getEmail());
            String message = emailService.sendSimpleMail(details);
            redirectAttributes.addFlashAttribute("message", message);
        }
        return "redirect:/users";
    }



}
