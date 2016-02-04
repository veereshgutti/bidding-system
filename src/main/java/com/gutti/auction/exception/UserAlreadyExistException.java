package com.gutti.auction.exception;

/**
 * Before user registration check whether user already register in system.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String messsage){
        super(messsage);
    }
}
