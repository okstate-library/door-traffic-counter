package com.okstatelibrary.doortrafficcounter.repository;

import org.springframework.data.repository.CrudRepository;

import com.okstatelibrary.doortrafficcounter.security.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}