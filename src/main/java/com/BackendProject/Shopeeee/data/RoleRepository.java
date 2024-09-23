package com.BackendProject.Shopeeee.data;

import com.BackendProject.Shopeeee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role,String> {

   Optional<Role> findByName(String role);

}
