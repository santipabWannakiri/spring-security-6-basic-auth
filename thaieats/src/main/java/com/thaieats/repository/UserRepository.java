package com.thaieats.repository;

import com.thaieats.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String userName);

}
