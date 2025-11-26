package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface ContractService {
    ApiResponse<List<ContractResponse>> getAllContracts();

    @EntityGraph(value = "Contract.detail")
    ApiResponse<Page<ContractResponse>> getAll(Integer page);
    ApiResponse<Page<ContractResponse>> searchByFullnameOrCccd(String keyword, Integer page);
    ApiResponse<ContractResponse> getById(Long id);
    ApiResponse<ContractResponse> create(CreateContractDto dto);
    ApiResponse<ContractResponse> update(Long id, Contract contract);
    ApiResponse<Void> delete(Long id);
}
