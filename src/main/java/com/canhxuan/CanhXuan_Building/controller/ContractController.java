package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canhxuan/contracts")
public class ContractController {
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAll() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contract>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
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
