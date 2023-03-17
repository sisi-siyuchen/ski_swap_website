package com.siyuchen.skiswap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Siyu Chen
 *  This class is to set up the beans for spring security
 */
@Configuration
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Customize the authentication provider based on userDetailsService and encoder
     * @return: authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); // set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); // set the password encoder
        return auth;
    }

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index","/login*", "/css/*", "/js/*", "/signup", "/signup-process").permitAll()
                .antMatchers("/home").hasAnyRole("ROLE_USER","ROLE_ADMIN")
                //.anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .usernameParameter("email")
                .loginProcessingUrl("/home")
                .successForwardUrl("/home")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll();
        return http.build();

    }

}
