package com.zocdoc.repository;

import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
   // List<Doctor> findBySpecialization(String specialization);
    boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByEmail(String email);

   List<Doctor> findByPermanentLocationContainingIgnoreCaseOrSpecializationContainingIgnoreCaseAndPincodeOrCityAndApproved(
          String permanentLocation, String specialization, String pincode, String city, Boolean approved);

    Optional<Doctor> findByPhoneNumberOrEmail(String phoneNumberOrEmail, String phoneNumberOrEmail1);
}
