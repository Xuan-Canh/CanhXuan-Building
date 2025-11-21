package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.request.InvoiceRequest;
import com.canhxuan.CanhXuan_Building.dto.request.ServiceUsageDetail;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceResponse;
import com.canhxuan.CanhXuan_Building.entity.*;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.repository.InvoiceRepository;
import com.canhxuan.CanhXuan_Building.repository.InvoiceServiceDetailRepository;
import com.canhxuan.CanhXuan_Building.repository.ServiceRepository;
import com.canhxuan.CanhXuan_Building.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ContractRepository contractRepository;
    private final InvoiceServiceDetailRepository invoiceServiceDetailRepository;
    private final ServiceRepository serviceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ContractRepository contractRepository, InvoiceServiceDetailRepository invoiceServiceDetailRepository, ServiceRepository serviceRepository) {
        this.invoiceRepository = invoiceRepository;
        this.contractRepository = contractRepository;
        this.invoiceServiceDetailRepository = invoiceServiceDetailRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ApiResponse<Page<InvoiceResponse>> getAll(Integer page) {
        Pageable pageable = PageRequest.of(Math.max(0, page), 10);
        ApiResponse<Page<InvoiceResponse>> apiResponse = new ApiResponse<>();

        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        Page<InvoiceResponse> responsePage = invoices.map(invoice -> {
            InvoiceResponse response = new InvoiceResponse();
            response.setId(invoice.getId());
            response.setContract(invoice.getContract());
            response.setInvoiceDate(invoice.getInvoiceDate());
            response.setDueDate(invoice.getDueDate());
            response.setRoomRent(invoice.getRoomRent());
            response.setTotalServiceFee(invoice.getTotalServiceFee());
            response.setTotalAmount(invoice.getTotalAmount());
            response.setStatus(invoice.getStatus());
            response.setNote(invoice.getNote());
            response.setPaidAt(invoice.getPaidAt());
            response.setServiceDetail(invoice.getServiceDetails());
            return response;
        });

        apiResponse.setData(responsePage);
        apiResponse.setMessage("Get all invoices successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponse<InvoiceResponse> getById(Long id) {
        ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>();
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            InvoiceResponse response = new InvoiceResponse();
            response.setId(invoice.getId());
            response.setContract(invoice.getContract());
            response.setInvoiceDate(invoice.getInvoiceDate());
            response.setDueDate(invoice.getDueDate());
            response.setRoomRent(invoice.getRoomRent());
            response.setTotalServiceFee(invoice.getTotalServiceFee());
            response.setTotalAmount(invoice.getTotalAmount());
            response.setStatus(invoice.getStatus());
            response.setNote(invoice.getNote());
            response.setPaidAt(invoice.getPaidAt());
            response.setServiceDetail(invoice.getServiceDetails());
            apiResponse.setData(response);
            apiResponse.setMessage("Get invoice by id successfully");
            apiResponse.setSuccess(true);
            return apiResponse;
        }
        return apiResponse;
    }

    @Override
    public ApiResponse<InvoiceResponse> create(InvoiceRequest createInvoiceRequest) {
        Contract contract = contractRepository.findById(createInvoiceRequest.getContractId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        Invoice invoice = new Invoice();
        Invoice lastInvoice = invoiceRepository.findTopByContractIdOrderByInvoiceDateDesc(contract.getId());
        if (lastInvoice == null) {
            lastInvoice = new Invoice();
            lastInvoice.setServiceDetails(new ArrayList<>());
        }
        List<InvoiceServiceDetail> details = new ArrayList<>();

        for (ServiceUsageDetail usage : createInvoiceRequest.getServiceUsageDetails()){
            InvoiceServiceDetail serviceDetail = new InvoiceServiceDetail();
            com.canhxuan.CanhXuan_Building.entity.Service service = serviceRepository.findById(usage.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            serviceDetail.setService(service);
            serviceDetail.setInvoice(invoice);
            serviceDetail.setUnitPrice(service.getPrice());
            if (service.getType().equals(ServiceType.METERED)) {
                serviceDetail.setOldReading(lastInvoice.getServiceDetails().stream()
                        .filter(detail -> detail.getService().getId().equals(usage.getServiceId()))
                        .findFirst()
                        .map(InvoiceServiceDetail::getNewReading)
                        .orElse(0.0));
                serviceDetail.setNewReading(usage.getNewReading());
                serviceDetail.setQuantity(serviceDetail.getNewReading() - serviceDetail.getOldReading());
            } else {
                serviceDetail.setQuantity(usage.getQuantity());
            }
            serviceDetail.setAmount(serviceDetail.getQuantity() * serviceDetail.getUnitPrice());
            details.add(serviceDetail);
        }
        invoice.setContract(contract);
        invoice.setServiceDetails(details);
        invoice.setInvoiceDate(createInvoiceRequest.getInvoiceDate());
        invoice.setDueDate(createInvoiceRequest.getDueDate());
        invoice.setRoomRent(contract.getMonthlyRent());
        invoice.setTotalServiceFee(details.stream().mapToDouble(InvoiceServiceDetail::getAmount).sum());
        invoice.setTotalAmount(invoice.getRoomRent() + invoice.getTotalServiceFee());
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setNote(createInvoiceRequest.getNote());
        Invoice saved = invoiceRepository.save(invoice);
        InvoiceResponse response = new InvoiceResponse();
        response.setId(saved.getId());
        response.setContract(saved.getContract());
        response.setInvoiceDate(saved.getInvoiceDate());
        response.setDueDate(saved.getDueDate());
        response.setRoomRent(saved.getRoomRent());
        response.setTotalServiceFee(saved.getTotalServiceFee());
        response.setTotalAmount(saved.getTotalAmount());
        response.setStatus(saved.getStatus());
        response.setNote(saved.getNote());
        response.setPaidAt(saved.getPaidAt());
        response.setServiceDetail(saved.getServiceDetails());
        ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Create invoice successfully");
        return apiResponse;
    }

    @Override
    public ApiResponse<InvoiceResponse> update(Long id, InvoiceRequest createInvoiceRequest) {
        return null;
    }

    @Override
    public ApiResponse<InvoiceResponse> markAsPaid(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        invoice = invoiceRepository.save(invoice);
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setContract(invoice.getContract());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setDueDate(invoice.getDueDate());
        response.setRoomRent(invoice.getRoomRent());
        response.setTotalServiceFee(invoice.getTotalServiceFee());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setStatus(invoice.getStatus());
        response.setNote(invoice.getNote());
        response.setPaidAt(invoice.getPaidAt());
        response.setServiceDetail(invoice.getServiceDetails());
        ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Mark invoice as paid successfully");
        apiResponse.setData(response);
        return null;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        invoiceRepository.deleteById(id);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Delete invoice successfully");
        return apiResponse;
    }
}
