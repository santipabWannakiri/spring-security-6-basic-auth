package com.thaieats.service;

import com.thaieats.model.User;

public interface UserService {
    public User findByUser(String userName);

    public User createUser(User userInfo);

}
