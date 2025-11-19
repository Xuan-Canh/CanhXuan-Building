package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.utils.kafka.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContractReportService {

    private final ContractRepository contractRepository;
    private final Producer producer;

    public byte[] exportContract(Long id) throws Exception {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        InputStream inputStream = getClass().getResourceAsStream("/reports/contract.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        InputStream subInputStream = getClass().getResourceAsStream("/reports/service_subreport.jrxml");
        JasperReport subReport = JasperCompileManager.compileReport(subInputStream);

        // Mapping đầy đủ parameters
        Map<String, Object> params = new HashMap<>();

        // Customer info
        params.put("customerFullname", contract.getCustomer().getFullname());
        params.put("customerPhone", contract.getCustomer().getPhone());
        params.put("customerEmail", contract.getCustomer().getEmail());
        params.put("customerAddress", contract.getCustomer().getAddress());
        params.put("customerCccd", contract.getCustomer().getCccd());
        params.put("customerDob", contract.getCustomer().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Room info
        params.put("roomName", contract.getRoom().getName());
        params.put("roomFloor", contract.getRoom().getFloor());
        params.put("roomCapacity", contract.getRoom().getCapacity());
        params.put("roomPrice", contract.getRoom().getPrice());
        params.put("roomDescription", contract.getRoom().getDescription());
        params.put("buildingName", contract.getRoom().getBuilding().getName());
        params.put("buildingAddress", contract.getRoom().getBuilding().getAddress());

        // Contract info
        params.put("startDate", contract.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("endDate", contract.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.put("monthlyRent", contract.getMonthlyRent());
        params.put("depositAmount", contract.getDepositAmount());
        params.put("paymentDueDate", contract.getPaymentDueDate());
        params.put("note", contract.getNote());

        // Services - chuyển thành JRBeanCollectionDataSource
        params.put("serviceDataSource", new JRBeanCollectionDataSource(contract.getServices()));
        params.put("SUBREPORT_DIR", subReport);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public void exportAndSendContract(Long id) throws Exception {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        try {
            String contractMessage = new ObjectMapper().writeValueAsString(id);
            producer.send("contract-topic", contractMessage);
            System.out.println("Contract sent to topic");
        } catch (Exception e) {
            throw new RuntimeException("Failed to export and send contract: " + e.getMessage(), e);
        }

    }
}
