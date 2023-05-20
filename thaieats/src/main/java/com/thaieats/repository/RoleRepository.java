package com.thaieats.repository;

import com.thaieats.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepo")
public interface RoleRepository extends CrudRepository<RoleModel,Long> {
    RoleModel findByName(String name);
}
