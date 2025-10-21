package com.example.salonmanagement.repository;

import com.example.salonmanagement.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("SELECT s FROM Staff s ORDER BY s.id DESC")
    List<Staff> findAllOrderByIdDesc();

    @Query("SELECT s FROM Staff s WHERE " +
            "(:name IS NULL OR :name = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:position IS NULL OR :position = '' OR s.position = :position) AND " +
            "(:specialization IS NULL OR :specialization = '' OR s.specialization = :specialization)")
    Page<Staff> findByFiltersPageable(
            @Param("name") String name,
            @Param("position") String position,
            @Param("specialization") String specialization,
            Pageable pageable
    );

    @Query("SELECT DISTINCT s.position FROM Staff s ORDER BY s.position")
    List<String> findDistinctPositions();

    @Query("SELECT DISTINCT s.specialization FROM Staff s ORDER BY s.specialization")
    List<String> findDistinctSpecializations();
}