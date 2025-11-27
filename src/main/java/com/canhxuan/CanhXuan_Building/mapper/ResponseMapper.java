package com.canhxuan.CanhXuan_Building.mapper;

import com.canhxuan.CanhXuan_Building.dto.response.*;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.entity.Room;
import com.canhxuan.CanhXuan_Building.entity.RoomImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {

    public CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setFullname(customer.getFullname());
        customerDTO.setCccd(customer.getCccd());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setDateOfBirth(customer.getDateOfBirth());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setStatus(customer.getStatus());
        customerDTO.setGender(customer.getGender());
        return customerDTO;
    }

    public RoomDTO toRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setFloor(room.getFloor());
        roomDTO.setCapacity(room.getCapacity());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setStatus(room.getStatus());
        roomDTO.setDescription(room.getDescription());
        return roomDTO;
    }

    public ImageDto toImageDto(RoomImage roomImage) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(roomImage.getId());
        imageDto.setFileName(roomImage.getFileName());
        imageDto.setFileType(roomImage.getFileType());
        imageDto.setFilePath(roomImage.getFilePath());
        return imageDto;
    }

    public ServiceDTO toServiceDTO(com.canhxuan.CanhXuan_Building.entity.Service service) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(service.getId());
        serviceDTO.setName(service.getName());
        serviceDTO.setDescription(service.getDescription());
        serviceDTO.setPrice(service.getPrice());
        serviceDTO.setType(service.getType());
        serviceDTO.setUnit(service.getUnit());
        return serviceDTO;
    }

    public ContractResponse toContractResponse(Contract contract) {
        ContractResponse contractResponse = new ContractResponse();
        contractResponse.setId(contract.getId());
        contractResponse.setStartDate(contract.getStartDate());
        contractResponse.setEndDate(contract.getEndDate());
        contractResponse.setDepositAmount(contract.getDepositAmount());
        contractResponse.setMonthlyRent(contract.getMonthlyRent());
        contractResponse.setPaymentDueDate(contract.getPaymentDueDate());
        contractResponse.setNote(contract.getNote());
        contractResponse.setCustomer(toCustomerDTO(contract.getCustomer()));
        contractResponse.setRoom(toRoomDTO(contract.getRoom()));
        List<ServiceDTO> serviceDTOs = contract.getServices().stream()
                .map(this::toServiceDTO)
                .collect(Collectors.toList());
        contractResponse.setServices(serviceDTOs);
        return contractResponse;
    }

    public InvoiceServiceDetailResponse toInvoiceServiceDetailResponse(com.canhxuan.CanhXuan_Building.entity.InvoiceServiceDetail detail) {
        InvoiceServiceDetailResponse response = new InvoiceServiceDetailResponse();
        response.setId(detail.getId());
        response.setService(toServiceDTO(detail.getService()));
        response.setOldReading(detail.getOldReading());
        response.setNewReading(detail.getNewReading());
        response.setQuantity(detail.getQuantity());
        response.setUnitPrice(detail.getUnitPrice());
        response.setAmount(detail.getAmount());
        return response;
    }

    public InvoiceResponse toInvoiceResponse(com.canhxuan.CanhXuan_Building.entity.Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setContract(toContractResponse(invoice.getContract()));
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setDueDate(invoice.getDueDate());
        response.setRoomRent(invoice.getRoomRent());
        response.setTotalServiceFee(invoice.getTotalServiceFee());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setStatus(invoice.getStatus());
        response.setPaidAt(invoice.getPaidAt());
        List<InvoiceServiceDetailResponse> detailResponses = invoice.getServiceDetails().stream()
                .map(this::toInvoiceServiceDetailResponse)
                .collect(Collectors.toList());
        response.setServiceDetail(detailResponses);
        return response;
    }
}
