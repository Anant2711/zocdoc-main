package com.zocdoc.config;

import com.zocdoc.entity.Admin;
import com.zocdoc.entity.Doctor;
import com.zocdoc.entity.Role;
import com.zocdoc.entity.User;
import com.zocdoc.repository.AdminRepository;
import com.zocdoc.repository.DoctorRepository;
import com.zocdoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumberOrEmail) throws UsernameNotFoundException {

        Optional<Admin> adminOptional = adminRepository.findByPhoneNumberOrEmail(phoneNumberOrEmail, phoneNumberOrEmail);
        Optional<Doctor> doctorOptional = doctorRepository.findByPhoneNumberOrEmail(phoneNumberOrEmail, phoneNumberOrEmail);
        Optional<User> userOptional = userRepository.findByPhoneNumberOrEmail(phoneNumberOrEmail, phoneNumberOrEmail);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getEmail(), admin.getPassword(), mapRolesToAuthorities(admin.getRoles()));

        } else if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    doctor.getEmail(), doctor.getPassword(), mapRolesToAuthorities(doctor.getRoles()));

        } else if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

        }else {
            throw new UsernameNotFoundException("User not found with username or email:" + phoneNumberOrEmail);
        }
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}