package com.example.salonmanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffSearchRequest {

    // Search filters
    private String name;
    private String position;
    private String specialization;

    // Pagination
    private int page = 0;
    private int size = 10;

    // Sorting
    private String sort = "id";
    private String direction = "desc";

    // Helper methods
    public boolean hasFilters() {
        return (name != null && !name.trim().isEmpty()) ||
                (position != null && !position.trim().isEmpty()) ||
                (specialization != null && !specialization.trim().isEmpty());
    }

    public boolean isAscending() {
        return "asc".equalsIgnoreCase(direction);
    }
}