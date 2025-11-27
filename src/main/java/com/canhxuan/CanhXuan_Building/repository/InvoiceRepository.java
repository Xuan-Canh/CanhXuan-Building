package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Invoice;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Invoice findTopByContractIdOrderByInvoiceDateDesc(Long id);
    boolean existsByIdAndCreatedByUsername(Long invoiceId, String username);
    Page<Invoice> findByCreatedBy(User createdBy, Pageable pageable);

    @Query("""
    SELECT i.id FROM Invoice i
    ORDER BY i.id DESC
""")
    Page<Long> findPageIds(Pageable pageable);

    @Query("""
    SELECT DISTINCT i FROM Invoice i
    JOIN FETCH i.contract c
    LEFT JOIN FETCH i.serviceDetails sd
    LEFT JOIN FETCH sd.service s
    WHERE i.id IN :ids
""")
    List<Invoice> findByIdsWithDetails(@Param("ids") List<Long> ids);

}
