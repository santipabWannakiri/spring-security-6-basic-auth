package com.thaieats.repository;

import com.thaieats.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepository extends CrudRepository<UserModel,Long> {

    UserModel findByEmail(String email);

}
