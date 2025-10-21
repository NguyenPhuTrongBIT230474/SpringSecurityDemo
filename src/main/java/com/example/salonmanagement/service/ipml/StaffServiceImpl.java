package com.example.salonmanagement.service.ipml;

import com.example.salonmanagement.entity.Staff;
import com.example.salonmanagement.repository.StaffRepository;
import com.example.salonmanagement.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Staff> searchStaff(String name, String position, String specialization, Pageable pageable) {
        log.info("Đang tìm kiếm nhân viên với phân trang...");
        return staffRepository.findByFiltersPageable(name, position, specialization, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Staff> getAllStaff() {
        log.info("Đang lấy tất cả nhân viên");
        return staffRepository.findAllOrderByIdDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Staff> getStaffById(Long id) {
        log.info("Tìm nhân viên với ID: {}", id);
        return staffRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getPositions() {
        log.info("Lấy danh sách chức vụ");
        return staffRepository.findDistinctPositions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getSpecializations() {
        log.info("Lấy danh sách chuyên môn");
        return staffRepository.findDistinctSpecializations();
    }
}