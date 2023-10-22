package com.zocdoc.service;

import com.zocdoc.entity.Admin;
import com.zocdoc.payload.AdminDTO;

import java.util.Optional;

public interface AdminService {
    public AdminDTO addAdmin(AdminDTO adminDTO);

    String approveDoctor(Long doctorId);

    String disapproveDoctor(Long doctorId);

}
