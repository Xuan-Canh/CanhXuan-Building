package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.InvoiceRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceResponse;
import com.canhxuan.CanhXuan_Building.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canhxuan/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAll() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(invoiceService.getById(Long.parseLong(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> create(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.ok(invoiceService.create(invoiceRequest));
    }
}
