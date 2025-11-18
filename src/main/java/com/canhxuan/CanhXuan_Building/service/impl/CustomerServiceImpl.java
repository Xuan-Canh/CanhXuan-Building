package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.repository.CustomerRepository;
import com.canhxuan.CanhXuan_Building.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public ApiResponse<List<Customer>> getAll() {
        ApiResponse<List<Customer>> response = new ApiResponse<>();
        response.setMessage("Get all customers successfully");
        response.setData(customerRepository.findAll());
        return response;
    }

    @Override
    public ApiResponse<Customer> getById(Long id) {
        ApiResponse<Customer> response = new ApiResponse<>();
        response.setMessage("Get customer by id successfully");
        response.setData(customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id)));
        return response;
    }

    @Override
    public ApiResponse<Customer> create(Customer customer) {
        ApiResponse<Customer> response = new ApiResponse<>();
        response.setMessage("Create customer successfully");
        response.setData(customerRepository.save(customer));
        return response;
    }

    @Override
    public ApiResponse<Customer> update(Long id, Customer customer) {
        Customer currentCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        currentCustomer.setFullname(customer.getFullname());
        currentCustomer.setAddress(customer.getAddress());
        currentCustomer.setDateOfBirth(customer.getDateOfBirth());
        currentCustomer.setCccd(customer.getCccd());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setEmail(customer.getEmail());
        ApiResponse<Customer> response = new ApiResponse<>();
        response.setMessage("Update customer successfully");
        response.setData(customerRepository.save(currentCustomer));
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        ApiResponse<Void> response = new ApiResponse<>();
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customerRepository.delete(customer);
        response.setMessage("Delete customer successfully");
        return response;
    }
}
