package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.service.CustomerService;
import com.canhxuan.CanhXuan_Building.service.impl.JasperService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/canhxuan/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JasperService jasperService;


    public CustomerController(CustomerService customerService, JasperService jasperService) {
        this.customerService = customerService;
        this.jasperService = jasperService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<ApiResponse<Page<Customer>>> getAll(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(customerService.getAll(page));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Customer>>> searchByFullnameOrCccd(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(customerService.searchByFullnameOrCccd(keyword, page));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<ApiResponse<Customer>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<byte[]> exportCustomersToExcel() throws Exception {
        byte[] data = jasperService.exportCustomersToExcel();
        String filename = "Danh_sach_khach_hang_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<ApiResponse<Customer>> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.create(customer));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<ApiResponse<Customer>> update(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(id, customer));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_MANAGE')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.delete(id));
    }
}
