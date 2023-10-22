package com.zocdoc.service;

import com.zocdoc.payload.DoctorDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO addDoctor(DoctorDTO doctorDTO);

    public List<DoctorDTO> searchDoctors(String place, String specialization, String pincode, String city);

    public List<DoctorDTO> getAllDoctors();
}
