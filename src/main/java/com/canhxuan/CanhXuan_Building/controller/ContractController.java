package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.service.ContractService;
import com.canhxuan.CanhXuan_Building.service.impl.JasperService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/canhxuan/contracts")
public class ContractController {
    private final ContractService contractService;
    private final JasperService jasperService;

    public ContractController(ContractService contractService, JasperService jasperService) {
        this.contractService = contractService;
        this.jasperService = jasperService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or hasAuthority('CONTRACT_READ_OWN')")
    public ResponseEntity<ApiResponse<Page<ContractResponse>>> getAll(@RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(contractService.getAll(page));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<java.util.List<ContractResponse>>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or hasAuthority('CONTRACT_READ_OWN')")
    public ResponseEntity<ApiResponse<Page<ContractResponse>>> searchByName(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(contractService.searchByFullnameOrCccd(keyword, page));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE')")
    public ResponseEntity<byte[]> exportContractsToExcel() throws Exception {
        byte[] data = jasperService.exportContractsToExcel();
        String filename = "Danh_sach_hop_dong_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or (hasAuthority('CONTRACT_READ_OWN') and @authHelper.isContractOwner(#id))")
    public ResponseEntity<ApiResponse<ContractResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    @GetMapping("/{id}/export")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or (hasAuthority('CONTRACT_READ_OWN') and @authHelper.isContractOwner(#id))")
    public ResponseEntity<byte[]> exportContract(@PathVariable Long id) {
        try {
            byte[] pdfBytes = jasperService.exportContract(id);

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
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or (hasAuthority('CONTRACT_READ_OWN') and @authHelper.isContractOwner(#id))")
    public ResponseEntity<ApiResponse<String>> sendContractEmail(@PathVariable Long id) {
        try {
            jasperService.exportAndSendContract(id);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .message("Email sent successfully")
                    .data("Contract has been sent to customer's email")
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send contract email: " + e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContractResponse>> create(@RequestBody CreateContractDto dto) {
        return ResponseEntity.ok(contractService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or " + "(hasAuthority('CONTRACT_UPDATE_OWN') and @authHelper.isContractOwner(#id))")
    public ResponseEntity<ApiResponse<ContractResponse>> update(@PathVariable Long id, @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.update(id, contract));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CONTRACT_MANAGE') or " + "(hasAuthority('CONTRACT_DELETE_OWN') and @authHelper.isContractOwner(#id))")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.delete(id));
    }
}
