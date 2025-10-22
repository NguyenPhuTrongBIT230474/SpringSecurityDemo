package com.example.salonmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @GetMapping("/403")
    public String accessDenied() {
        return "Bạn không có quyền truy cập vào trang này.";
    }
}