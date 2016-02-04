package com.gutti.auction.exception;

/**
 * Bid violates minimum price criteria for item to bid.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class MinPriceException extends Exception {
    public MinPriceException(String messsage){
        super(messsage);
    }
}
