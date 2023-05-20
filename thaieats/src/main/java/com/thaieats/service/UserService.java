package com.thaieats.service;

import com.thaieats.model.UserModel;

public interface UserService {
    public UserModel findByEmail(String email);

    public UserModel createUser(UserModel userInfo);

}
