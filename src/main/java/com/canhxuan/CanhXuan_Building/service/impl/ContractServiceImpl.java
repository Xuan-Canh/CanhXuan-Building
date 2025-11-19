package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.request.CreateContractDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ContractResponse;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.repository.CustomerRepository;
import com.canhxuan.CanhXuan_Building.repository.RoomRepository;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import com.canhxuan.CanhXuan_Building.service.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    public ContractServiceImpl(ContractRepository contractRepository, UserRepository userRepository, CustomerRepository customerRepository, RoomRepository roomRepository) {
        this.contractRepository = contractRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public ApiResponse<List<ContractResponse>> getAll() {
        ApiResponse<List<ContractResponse>> response = new ApiResponse<>();
        response.setMessage("Get all contracts successfully");
        response.setData(contractRepository.findAll().stream().map(contract ->{
            ContractResponse contractResponse = new ContractResponse();
            contractResponse.setId(contract.getId());
            contractResponse.setCustomer(contract.getCustomer());
            contractResponse.setRoom(contract.getRoom());
            contractResponse.setStartDate(contract.getStartDate());
            contractResponse.setEndDate(contract.getEndDate());
            contractResponse.setMonthlyRent(contract.getMonthlyRent());
            contractResponse.setDepositAmount(contract.getDepositAmount());
            contractResponse.setNote(contract.getNote());
            contractResponse.setPaymentDueDate(contract.getPaymentDueDate());
            contractResponse.setService(contract.getServices());
            return contractResponse;
        }).collect(Collectors.toList()));
        return response;
    }

    @Override
    public ApiResponse<Contract> getById(Long id) {
        ApiResponse<Contract> response = new ApiResponse<>();
        response.setMessage("Get contract successfully");
        response.setData(contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id)));
        return response;
    }

    @Override
    public ApiResponse<Contract> create(CreateContractDto dto) {
        Contract contract = new Contract();
        Optional<Customer> customerOptional = customerRepository.findByCccd(dto.getCustomer().getCccd());
        if(customerOptional.isEmpty()){
            Customer customer;
            customer = dto.getCustomer();
            contract.setCustomer(customerRepository.save(customer));
        } else {
            contract.setCustomer(customerOptional.get());
        }
        contract.setRoom(roomRepository.findById(dto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found with id: " + dto.getRoomId())));
        contract.setStartDate(dto.getStartDate());
        contract.setEndDate(dto.getEndDate());
        contract.setMonthlyRent(dto.getMonthlyRent());
        contract.setDepositAmount(dto.getDepositAmount());
        contract.setNote(dto.getNote());
        contract.setPaymentDueDate(dto.getPaymentDueDate());
        contract.setServices(dto.getServices());
        ApiResponse<Contract> response = new ApiResponse<>();
        response.setMessage("Create contract successfully");
        response.setData(contractRepository.save(contract));
        return response;
    }

    @Override
    public ApiResponse<Contract> update(Long id, Contract contract) {
        Contract currentContract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        currentContract.setStartDate(contract.getStartDate());
        currentContract.setEndDate(contract.getEndDate());
        currentContract.setMonthlyRent(contract.getMonthlyRent());
        currentContract.setDepositAmount(contract.getDepositAmount());
        currentContract.setNote(contract.getNote());
        currentContract.setPaymentDueDate(contract.getPaymentDueDate());
        currentContract.setServices(contract.getServices());
        currentContract.setCustomer(contract.getCustomer());
        ApiResponse<Contract> response = new ApiResponse<>();
        response.setMessage("Update contract successfully");
        response.setData(contractRepository.save(currentContract));
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        ApiResponse<Void> response = new ApiResponse<>();
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
        contractRepository.delete(contract);
        response.setMessage("Delete contract successfully");
        return response;
    }
}
