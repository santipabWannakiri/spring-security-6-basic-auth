package com.thaieats.repository;

import com.thaieats.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("RoleRepository")
public interface RoleRepository extends CrudRepository<Role,Integer> {
    Role findByName(String name);
}
