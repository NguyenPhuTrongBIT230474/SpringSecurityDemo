package com.example.salonmanagement.util;

import com.example.salonmanagement.entity.Staff;
import com.example.salonmanagement.entity.Role;
import com.example.salonmanagement.entity.User;
import com.example.salonmanagement.repository.StaffRepository;
import com.example.salonmanagement.repository.RoleRepository;
import com.example.salonmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository; // Đã đổi
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            log.info("Tạo dữ liệu Role mẫu...");
            createSampleRoles();
        }

        if (userRepository.count() == 0) {
            log.info("Tạo dữ liệu User và Staff mẫu...");
            createSampleUsersAndStaff(); // Đã đổi
        }
    }

    private void createSampleRoles() {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_STAFF"));
    }

    private void createSampleUsersAndStaff() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        Role staffRole = roleRepository.findByName("ROLE_STAFF").orElseThrow();

        // --- Tạo Admin ---
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRoles(Set.of(adminRole, staffRole));

        Staff adminStaff = new Staff(); // Đã đổi
        adminStaff.setName("Admin Salon");
        adminStaff.setPosition("Quản lý");
        adminStaff.setSpecialization("Quản lý chung");
        adminStaff.setWorkShift(Staff.WorkShift.CA_NGAY); // Đã đổi
        adminStaff.setSalary(new BigDecimal("20000000"));
        adminStaff.setUser(adminUser);

        adminUser.setStaff(adminStaff); // Đã đổi
        userRepository.save(adminUser);

        // --- Tạo Staff (Theo yêu cầu của bạn) ---
        User staffUser = new User();
        staffUser.setUsername("staff01");
        staffUser.setPassword(passwordEncoder.encode("staff123"));
        staffUser.setRoles(Set.of(staffRole));

        Staff staffMember = new Staff(); // Đã đổi
        staffMember.setName("Nguyễn Thị Lan");
        staffMember.setPosition("Staff"); // Chức vụ mặc định
        staffMember.setSpecialization("Chăm sóc da");
        staffMember.setWorkShift(Staff.WorkShift.SANG); // Đã đổi
        staffMember.setSalary(new BigDecimal("8500000"));
        staffMember.setUser(staffUser);

        staffUser.setStaff(staffMember); // Đã đổi
        userRepository.save(staffUser);
    }
}