package com.thaieats.serviceImp;

import com.thaieats.model.RoleModel;
import com.thaieats.model.UserModel;
import com.thaieats.repository.RoleRepository;
import com.thaieats.repository.UserRepository;
import com.thaieats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    @Override
    public UserModel findByEmail(String email) {
        return  userRepo.findByEmail(email);
    }

    @Override
    public UserModel createUser(UserModel userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        RoleModel tempRole = roleRepo.findByName("ROLE_CUSTOMER");
        userInfo.setRoles(new HashSet<RoleModel>(Arrays.asList(tempRole)));
        userInfo.setIsActive(true);
        return userRepo.save(userInfo);
    }
}
