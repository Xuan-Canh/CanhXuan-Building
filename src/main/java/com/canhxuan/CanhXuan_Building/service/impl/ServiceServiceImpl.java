package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.repository.ServiceRepository;
import com.canhxuan.CanhXuan_Building.service.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public ApiResponse<Page<com.canhxuan.CanhXuan_Building.entity.Service>> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        ApiResponse<Page<com.canhxuan.CanhXuan_Building.entity.Service>> response = new ApiResponse<>();
        response.setData(serviceRepository.findAll(pageable));
        response.setMessage("Get all services successfully");
        return response;
    }

    @Override
    public ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> getById(Long id) {
        ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> response = new ApiResponse<>();
        com.canhxuan.CanhXuan_Building.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        response.setData(service);
        response.setMessage("Get service successfully");
        return response;
    }

    @Override
    public ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> create(com.canhxuan.CanhXuan_Building.entity.Service service) {
        ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> response = new ApiResponse<>();
        com.canhxuan.CanhXuan_Building.entity.Service savedService = serviceRepository.save(service);
        response.setData(savedService);
        response.setMessage("Create service successfully");
        return response;
    }

    @Override
    public ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> update(Long id, com.canhxuan.CanhXuan_Building.entity.Service service) {
        ApiResponse<com.canhxuan.CanhXuan_Building.entity.Service> response = new ApiResponse<>();
        com.canhxuan.CanhXuan_Building.entity.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        existingService.setName(service.getName());
        existingService.setDescription(service.getDescription());
        existingService.setPrice(service.getPrice());

        com.canhxuan.CanhXuan_Building.entity.Service updatedService = serviceRepository.save(existingService);
        response.setData(updatedService);
        response.setMessage("Update service successfully");
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        ApiResponse<Void> response = new ApiResponse<>();
        com.canhxuan.CanhXuan_Building.entity.Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        serviceRepository.delete(existingService);
        response.setMessage("Delete service successfully");
        return response;
    }
}
