package com.auth.test.repository;

import com.auth.test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

   Role findByName(String name);
}
