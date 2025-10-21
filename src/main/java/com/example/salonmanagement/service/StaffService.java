package com.example.salonmanagement.service;

import com.example.salonmanagement.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StaffService {

    Page<Staff> searchStaff(String name, String position, String specialization, Pageable pageable);

    List<Staff> getAllStaff();

    Optional<Staff> getStaffById(Long id);

    List<String> getPositions();

    List<String> getSpecializations();
}