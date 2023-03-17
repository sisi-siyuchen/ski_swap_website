package com.siyuchen.skiswap.exception;

/**
 * @author Siyu Chen
 * Exception for user not found
 */
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
