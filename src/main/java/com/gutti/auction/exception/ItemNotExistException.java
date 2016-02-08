package com.gutti.auction.exception;

/**
 * Item Not Exist in bidding system to bid.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class ItemNotExistException extends Exception {
    public ItemNotExistException(String messsage){
        super(messsage);
    }
}
