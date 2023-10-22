package com.zocdoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zocdoc.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);;

}
