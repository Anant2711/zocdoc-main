package com.zocdoc.repository;

import com.zocdoc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
   // boolean existsByPhoneNumberOrEmail(String phoneNumber, String email);
    Boolean existsByEmail(String email);

    Optional<User> findByPhoneNumberOrEmail(String phoneNumberOrEmail, String phoneNumberOrEmail1);
}
