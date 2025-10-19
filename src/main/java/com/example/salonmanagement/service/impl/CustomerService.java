package com.example.salonmanagement.service;

import com.example.salonmanagement.model.Customer;
import com.example.salonmanagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy danh sách khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    // Thêm khách hàng
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Sắp xếp theo tên
    public List<Customer> sortByName(boolean asc) {
        List<Customer> list = customerRepository.findAll();
        list.sort(asc ? Comparator.comparing(Customer::getName)
                : Comparator.comparing(Customer::getName).reversed());
        return list;
    }

}
