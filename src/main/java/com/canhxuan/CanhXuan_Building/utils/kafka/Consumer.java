package com.canhxuan.CanhXuan_Building.utils.kafka;

import com.canhxuan.CanhXuan_Building.dto.request.MailDto;
import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.repository.ContractRepository;
import com.canhxuan.CanhXuan_Building.service.impl.JasperService;
import com.canhxuan.CanhXuan_Building.service.impl.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private final ContractRepository contractRepository;
    private final JasperService contractReportService;
    private final EmailService emailService;

    public Consumer(ContractRepository contractRepository, JasperService contractReportService, EmailService emailService) {
        this.contractRepository = contractRepository;
        this.contractReportService = contractReportService;
        this.emailService = emailService;
    }

    @Transactional
    @RetryableTopic(attempts = "1", backoff = @Backoff(delay = 2000))
    @KafkaListener(topics = "contract-topic", groupId = "CX-Apartment")
    public void consumeContractTopic(String message) throws Exception {
        Long contractId = new ObjectMapper().readValue(message, Long.class);
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        byte[] pdfBytes = contractReportService.exportContract(contract.getId());

        emailService.sendContractEmail(
                contract.getCustomer().getEmail(),
                contract.getCustomer().getFullname(),
                contract.getId(),
                pdfBytes
        );
        System.out.println("Contract sent to customer: " + contract.getCustomer().getFullname());
    }

    @KafkaListener(topics = "auth-topic", groupId = "CX-Apartment")
    public void consumeAuthTopic(String message) throws JsonProcessingException {
        System.out.println("Received message in auth-topic: " + message);
        MailDto mailDto = new ObjectMapper().readValue(message, MailDto.class);
        emailService.sendEmail(mailDto.getTo(), mailDto.getSubject(), mailDto.getBody());
        System.out.println("Email sent to: " + mailDto.getTo());
    }

    @KafkaListener(topics = "invoice-topic", groupId = "CX-Apartment")
    public void consumeInvoiceTopic(String message) throws JsonProcessingException {
        System.out.println("Received message in invoice-topic: " + message);
        MailDto mailDto = new ObjectMapper().readValue(message, MailDto.class);
        emailService.sendEmail(mailDto.getTo(), mailDto.getSubject(), mailDto.getBody());
        System.out.println("Email sent to: " + mailDto.getTo());
    }
}
