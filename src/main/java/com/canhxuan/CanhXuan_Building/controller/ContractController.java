package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.service.ContractService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canhxuan/contracts")
public class ContractController {
    private final ContractService contractService;
    private final com.canhxuan.CanhXuan_Building.service.impl.ContractReportService contractReportService;

    public ContractController(ContractService contractService, com.canhxuan.CanhXuan_Building.service.impl.ContractReportService contractReportService) {
        this.contractService = contractService;
        this.contractReportService = contractReportService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAll() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contract>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportContract(@PathVariable Long id) {
        try {
            byte[] pdfBytes = contractReportService.exportContract(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "contract_" + id + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/send-email")
    public ResponseEntity<ApiResponse<String>> sendContractEmail(@PathVariable Long id) {
        try {
            contractReportService.exportAndSendContract(id);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .message("Email sent successfully")
                    .data("Contract has been sent to customer's email")
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send contract email: " + e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Contract>> create(@RequestBody CreateContractDto dto) {
        return ResponseEntity.ok(contractService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Contract>> update(@PathVariable Long id, @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.update(id, contract));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.delete(id));
    }
}
