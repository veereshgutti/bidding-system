package com.gutti.auction.core;

import com.gutti.auction.entity.User;
import com.gutti.auction.exception.UserAlreadyExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Facilitate User registration for bidding process, validates whether user already registered or not before bidding on item.
 * Separates user registration from auction system
 * Created by Veeresh Gutti on 3/2/16.
 */
public class UserBase {
    private static UserBase userBase;
    private Map<Integer,User> users =new ConcurrentHashMap<>();

    private UserBase() {
    }

    public static synchronized UserBase getInstance(){
        if(userBase == null)
            userBase=new UserBase();
        return userBase;
    }

    /**
     * User can register using this method to participate in bidding process
     * @param user - User entity with id, Name, address
     * @throws UserAlreadyExistException
     */
    public synchronized void registerUser(User user) throws UserAlreadyExistException {
        if(users.get(user.getUserId()) != null)
            throw new UserAlreadyExistException("User already Registered: " + user.getUserId());
        users.put(user.getUserId(), user);
    }

    public synchronized List<User> getRegisteredUsers(){
        List userList = new ArrayList();
        for (Map.Entry<Integer, User> integerUserEntry : users.entrySet()) {
            userList.add(integerUserEntry.getValue());
        }
        return userList;
    }

    public synchronized boolean isUserExist(int userId){
        return users.containsKey(userId);
    }

    public synchronized User getUserDetails(int userId){
        return users.get(userId);
    }
}
