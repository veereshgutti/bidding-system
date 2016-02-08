package com.gutti.auction.exception;

/**
 * When user tries to bid item which is not put for auction.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class UserNotExistException extends Exception {
    public UserNotExistException(String messsage){
        super(messsage);
    }
}
