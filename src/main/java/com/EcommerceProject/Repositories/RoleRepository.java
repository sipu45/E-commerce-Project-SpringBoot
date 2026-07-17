package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.AppRole;
import com.EcommerceProject.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
