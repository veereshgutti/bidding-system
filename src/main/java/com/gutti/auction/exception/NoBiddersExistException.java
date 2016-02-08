package com.gutti.auction.exception;

/**
 * When item expires without any user to bid.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class NoBiddersExistException extends Exception {
    public NoBiddersExistException(String messsage){
        super(messsage);
    }
}
