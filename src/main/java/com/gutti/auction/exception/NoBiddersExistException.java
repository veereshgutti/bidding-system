package com.gutti.auction.exception;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class NoBiddersExistException extends Exception {
    public NoBiddersExistException(String messsage){
        super(messsage);
    }
}
