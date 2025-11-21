package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.InvoiceRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceResponse;
import com.canhxuan.CanhXuan_Building.service.InvoiceService;
import com.canhxuan.CanhXuan_Building.service.impl.JasperService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/canhxuan/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final JasperService jasperService;

    public InvoiceController(InvoiceService invoiceService, JasperService jasperService) {
        this.invoiceService = invoiceService;
        this.jasperService = jasperService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<InvoiceResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(invoiceService.getAll(page));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportInvoicesToExcel() throws Exception {
        byte[] data = jasperService.exportInvoicesToExcel();
        String filename = "Danh_sach_hoa_don_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(invoiceService.getById(Long.parseLong(id)));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> create(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.ok(invoiceService.create(invoiceRequest));
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<ApiResponse<InvoiceResponse>> markAsPaid(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.markAsPaid(id));
    }
}
