package com.canhxuan.CanhXuan_Building.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(name = "Contract.detail", attributeNodes = {
        @NamedAttributeNode(value = "customer"),
        @NamedAttributeNode(value = "room"),
        @NamedAttributeNode(value = "services")
})
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contract_services",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    List<Service> services;

    @NotNull(message = "Start date is required")
    LocalDate startDate;
    @NotNull(message = "End date is required")
    LocalDate endDate;

    @NotNull(message = "Monthly rent is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monthly rent must be greater than 0")
    Double monthlyRent;

    @DecimalMin(value = "0.0", message = "Deposit amount must be non-negative")
    Double depositAmount;

    @NotNull(message = "Payment due date is required")
    @Min(value = 1, message = "Payment due date must be between 1 and 31")
    @Max(value = 31, message = "Payment due date must be between 1 and 31")
    Integer paymentDueDate;

    @Enumerated(EnumType.STRING)
    ContractStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    String note;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
