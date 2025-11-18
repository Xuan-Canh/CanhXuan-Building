package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Service;

import java.util.List;

public interface ServiceService {
    ApiResponse<List<Service>> getAll();
    ApiResponse<Service> getById(Long id);
    ApiResponse<Service> create(Service service);
    ApiResponse<Service> update(Long id, Service service);
    ApiResponse<Void> delete(Long id);
}
