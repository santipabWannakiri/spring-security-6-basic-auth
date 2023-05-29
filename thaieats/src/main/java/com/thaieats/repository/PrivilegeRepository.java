package com.thaieats.repository;

import com.thaieats.model.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("PrivilegeRepository")
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer> {


}
