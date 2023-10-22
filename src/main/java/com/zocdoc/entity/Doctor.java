package com.zocdoc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String permanentLocation;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String password;
    private String experience;
    private Boolean approved;

    private String city;
    private String pincode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_roles",
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
//
//    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Appointment> appointments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
//    private List<TimeSlot> timeSlots;

}


