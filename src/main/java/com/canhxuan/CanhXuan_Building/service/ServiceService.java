package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Service;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceService {
    ApiResponse<Page<Service>> getAll(Integer page);
    ApiResponse<Service> getById(Long id);
    ApiResponse<Service> create(Service service);
    ApiResponse<Service> update(Long id, Service service);
    ApiResponse<Void> delete(Long id);
}
