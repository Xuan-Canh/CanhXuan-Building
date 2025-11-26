package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Service;
import com.canhxuan.CanhXuan_Building.service.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canhxuan/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Service>>> getAll(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(serviceService.getAll(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Service>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SERVICE_MANAGE')")
    public ResponseEntity<ApiResponse<Service>> create(@RequestBody Service service) {
        return ResponseEntity.ok(serviceService.create(service));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SERVICE_MANAGE')")
    public ResponseEntity<ApiResponse<Service>> update(@PathVariable Long id, @RequestBody Service service) {
        return ResponseEntity.ok(serviceService.update(id, service));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SERVICE_MANAGE')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.delete(id));
    }
}
