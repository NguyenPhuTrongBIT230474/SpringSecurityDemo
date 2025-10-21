package com.example.salonmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "staff") // Đổi tên bảng
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff { // Đổi tên class

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String name; // Tên nhân viên

    @Column(length = 100)
    private String position; // Chức vụ (ví dụ: "Staff", "Quản lý")

    @Column(length = 200)
    private String specialization; // Chuyên môn

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WorkShift workShift; // Ca làm việc

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(precision = 12, scale = 2)
    private BigDecimal salary; // Lương

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Quan hệ 1-1 với User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // Enum cho Ca làm việc
    public enum WorkShift {
        SANG("Ca Sáng"),
        CHIEU("Ca Chiều"),
        CA_NGAY("Cả Ngày");

        private final String displayName;

        WorkShift(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}