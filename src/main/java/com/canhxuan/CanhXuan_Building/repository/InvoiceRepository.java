package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Invoice;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice findTopByContractIdOrderByInvoiceDateDesc(Long id);
    boolean existsByIdAndCreatedByUsername(Long invoiceId, String username);
    Page<Invoice> findByCreatedBy(User createdBy, Pageable pageable);
}
