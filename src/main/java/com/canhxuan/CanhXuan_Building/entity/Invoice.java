package com.canhxuan.CanhXuan_Building.entity;


import com.canhxuan.CanhXuan_Building.dto.response.InvoiceDashboardDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SqlResultSetMapping(
        name = "InvoiceDashboardMapping",
        classes = @ConstructorResult(
                targetClass = InvoiceDashboardDto.class,
                columns = {
                        @ColumnResult(name = "monthlyRevenue", type = Double.class),
                        @ColumnResult(name = "unpaidInvoices", type = Long.class)
                }
        )
)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    Contract contract;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    java.util.List<InvoiceServiceDetail> serviceDetails;

    @Column(nullable = false)
    LocalDate invoiceDate;

    @Column(nullable = false)
    LocalDate dueDate;

    @Column(nullable = false)
    Double roomRent; // Tiền phòng

    @Column(nullable = false)
    Double totalServiceFee; // Tổng tiền dịch vụ

    @Column(nullable = false)
    Double totalAmount; // Tổng tiền

    @Enumerated(EnumType.STRING)
    InvoiceStatus status; // UNPAID, PAID, OVERDUE

    LocalDateTime paidAt;

    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @JsonIgnore
    User createdBy;

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
