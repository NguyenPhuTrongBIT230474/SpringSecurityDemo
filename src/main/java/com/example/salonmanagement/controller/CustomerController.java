package com.example.salonmanagement.controller;

import com.example.salonmanagement.model.Customer;
import com.example.salonmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Lấy danh sách khách hàng
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    // Cập nhật khách hàng
    @PutMapping("/{id}")
    public Optional<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer updated) {
        return customerService.updateCustomer(id, updated);
    }

    // Thêm khách hàng mới
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    // Xoá khách hàng
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
    // Tìm kiếm khách hàng
    @GetMapping("/search")
    public List<Customer> searchCustomer(@RequestParam String keyword) {
        return customerService.searchCustomers(keyword);
    }



}
