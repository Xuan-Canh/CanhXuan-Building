package com.canhxuan.CanhXuan_Building.service;


import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Customer;

import java.util.List;

public interface CustomerService {

    ApiResponse<List<Customer>> getAll();

    ApiResponse<Customer> getById(Long id);

    ApiResponse<Customer> create(Customer customer);

    ApiResponse<Customer> update(Long id, Customer customer);

    ApiResponse<Void> delete(Long id);
}
