package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractService {
    ApiResponse<List<ContractResponse>> getAllContracts();
    ApiResponse<Page<ContractResponse>> getAll(Integer page);
    ApiResponse<Page<ContractResponse>> searchByName(String name, Integer page);
    ApiResponse<Contract> getById(Long id);
    ApiResponse<Contract> create(CreateContractDto dto);
    ApiResponse<Contract> update(Long id, Contract contract);
    ApiResponse<Void> delete(Long id);
}
