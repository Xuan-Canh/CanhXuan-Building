package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.*;
import com.canhxuan.CanhXuan_Building.entity.*;
import com.canhxuan.CanhXuan_Building.mapper.ResponseMapper;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.repository.CustomerRepository;
import com.canhxuan.CanhXuan_Building.repository.RoomRepository;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import com.canhxuan.CanhXuan_Building.service.ContractService;
import com.canhxuan.CanhXuan_Building.utils.AuthHelper;
import com.canhxuan.CanhXuan_Building.utils.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final AuthHelper authHelper;
    private final JwtUtil jwtUtil;
    private final ResponseMapper responseMapper;

    public ContractServiceImpl(ContractRepository contractRepository, UserRepository userRepository, CustomerRepository customerRepository, RoomRepository roomRepository, UserRepository userRepository1, AuthHelper authHelper, JwtUtil jwtUtil, ResponseMapper responseMapper) {
        this.contractRepository = contractRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository1;
        this.authHelper = authHelper;
        this.jwtUtil = jwtUtil;
        this.responseMapper = responseMapper;
    }


    @Override
    public ApiResponse<List<ContractResponse>> getAllContracts() {
        ApiResponse<List<ContractResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get all contracts successfully");
        apiResponse.setData(contractRepository.findAll().stream().map(contract -> {
            ContractResponse contractResponse = responseMapper.toContractResponse(contract);
            return contractResponse;
        }).collect(Collectors.toList()));
        return apiResponse;
    }

    @Override
    public ApiResponse<Page<ContractResponse>> getAll(Integer page) {
        authHelper.requirePermission(Permission.CONTRACT_MANAGE, Permission.CONTRACT_READ_OWN);

        User currentUser = authHelper.getCurrentUser();

        ApiResponse<Page<ContractResponse>> response = new ApiResponse<>();
        int p = page == null ? 0 : Math.max(0, page);
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(p, 10);

        Page<Contract> contracts;

        if (currentUser.getRole().hasPermission(Permission.CONTRACT_MANAGE)) {
            contracts = contractRepository.loadAllWithGraph(pageable);
        } else {
            contracts = contractRepository.findByCreatedBy(currentUser, pageable);
        }

        Page<ContractResponse> responsePage = contracts.map(contract -> {
            ContractResponse contractResponse = responseMapper.toContractResponse(contract);
            return contractResponse;
        });

        response.setData(responsePage);
        response.setMessage("Get all contracts successfully");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse<Page<ContractResponse>> searchByFullnameOrCccd(String keyword, Integer page) {
        authHelper.requirePermission(Permission.CONTRACT_MANAGE, Permission.CONTRACT_READ_OWN);
        User currentUser = authHelper.getCurrentUser();

        ApiResponse<Page<ContractResponse>> response = new ApiResponse<>();
        int p = page == null ? 0 : Math.max(0, page);
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(p, 10);

        Page<Contract> contracts;

        if (currentUser.getRole().hasPermission(Permission.CONTRACT_MANAGE)) {
            contracts = contractRepository.findByCustomerFullnameContainingIgnoreCaseOrCustomerCccdContaining(keyword, keyword, pageable);
        } else {
            contracts = contractRepository.findByCreatedByAndKeyword(currentUser, keyword, pageable);
        }

        Page<ContractResponse> responsePage = contracts.map(contract -> {
            ContractResponse contractResponse = responseMapper.toContractResponse(contract);
            return contractResponse;
        });

        response.setData(responsePage);
        response.setMessage("Search contracts by customer name successfully");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ApiResponse<ContractResponse> getById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        ContractResponse contractResponse = responseMapper.toContractResponse(contract);
        ApiResponse<ContractResponse> response = new ApiResponse<>();
        response.setMessage("Get contract successfully");
        response.setSuccess(true);
        response.setData(contractResponse);
        return response;
    }

    @Override
    public ApiResponse<ContractResponse> create(CreateContractDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + dto.getRoomId()));
        if (room.getStatus().equals(RoomStatus.OCCUPIED) || room.getStatus().equals(RoomStatus.MAINTENANCE)) {
            throw new RuntimeException("Room is not available for new contract");
        }
        Contract contract = new Contract();
        Optional<Customer> customerOptional = customerRepository.findByCccd(dto.getCustomer().getCccd());
        if(customerOptional.isEmpty()){
            Customer customer;
            customer = dto.getCustomer();
            contract.setCustomer(customerRepository.save(customer));
        } else {
            contract.setCustomer(customerOptional.get());
        }
        contract.setRoom(room);
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        contract.setMonthlyRent(dto.getMonthlyRent());
        contract.setDepositAmount(dto.getDepositAmount());
        contract.setNote(dto.getNote());
        contract.setPaymentDueDate(dto.getPaymentDueDate());
        contract.setServices(dto.getServices());
        contract.setStatus(ContractStatus.ACTIVE);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        contract.setCreatedBy(userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username)));
        contract = contractRepository.save(contract);
        ContractResponse contractResponse = responseMapper.toContractResponse(contract);
        ApiResponse<ContractResponse> response = new ApiResponse<>();
        response.setMessage("Create contract successfully");
        response.setSuccess(true);
        response.setData(contractResponse);
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);
        return response;
    }

    @Override
    public ApiResponse<ContractResponse> update(Long id, Contract contract) {
        Contract currentContract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        Customer currentCustomer = customerRepository.findById(contract.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + contract.getCustomer().getCccd()));
        currentCustomer.setCccd(contract.getCustomer().getCccd());
        currentCustomer.setFullname(contract.getCustomer().getFullname());
        currentCustomer.setEmail(contract.getCustomer().getEmail());
        currentCustomer.setPhone(contract.getCustomer().getPhone());
        currentCustomer.setDateOfBirth(contract.getCustomer().getDateOfBirth());
        currentCustomer.setAddress(contract.getCustomer().getAddress());
        currentCustomer.setGender(contract.getCustomer().getGender());
        customerRepository.save(currentCustomer);
        currentContract.setStartDate(contract.getStartDate());
        currentContract.setEndDate(contract.getEndDate());
        currentContract.setMonthlyRent(contract.getMonthlyRent());
        currentContract.setDepositAmount(contract.getDepositAmount());
        currentContract.setNote(contract.getNote());
        currentContract.setPaymentDueDate(contract.getPaymentDueDate());
        currentContract.setServices(contract.getServices());
        currentContract.setCustomer(currentCustomer);
        currentContract = contractRepository.save(currentContract);
        ContractResponse contractResponse = responseMapper.toContractResponse(currentContract);
        ApiResponse<ContractResponse> response = new ApiResponse<>();
        response.setMessage("Update contract successfully");
        response.setSuccess(true);
        response.setData(contractResponse);
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        ApiResponse<Void> response = new ApiResponse<>();
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        contractRepository.delete(contract);
        response.setMessage("Delete contract successfully");
        response.setSuccess(true);
        return response;
    }
}
