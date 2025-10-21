package com.example.salonmanagement.controller;

import com.example.salonmanagement.dto.StaffSearchRequest;
import com.example.salonmanagement.entity.Staff;
import com.example.salonmanagement.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/staff") // Đổi đường dẫn
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @GetMapping
    public String listStaff(@ModelAttribute StaffSearchRequest searchRequest, Model model) {

        log.info("Hiển thị danh sách nhân viên với search request: {}", searchRequest);

        Sort.Direction sortDirection = searchRequest.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, searchRequest.getSort());

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sortBy);

        Page<Staff> staffPage = staffService.searchStaff(
                searchRequest.getName(),
                searchRequest.getPosition(),
                searchRequest.getSpecialization(),
                pageable
        );

        List<String> positions = staffService.getPositions();
        List<String> specializations = staffService.getSpecializations();

        model.addAttribute("staffPage", staffPage);
        model.addAttribute("staffList", staffPage.getContent());
        model.addAttribute("positions", positions);
        model.addAttribute("specializations", specializations);
        model.addAttribute("shifts", Staff.WorkShift.values());
        model.addAttribute("searchRequest", searchRequest);

        model.addAttribute("totalPages", staffPage.getTotalPages());
        model.addAttribute("totalElements", staffPage.getTotalElements());
        model.addAttribute("currentPage", searchRequest.getPage());

        model.addAttribute("searchName", searchRequest.getName());
        model.addAttribute("searchPosition", searchRequest.getPosition());
        model.addAttribute("searchSpecialization", searchRequest.getSpecialization());

        return "staff/list"; // Trả về file list.html trong templates/staff/
    }
}