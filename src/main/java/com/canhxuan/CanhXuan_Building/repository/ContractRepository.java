package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Page<Contract> findByCustomerFullnameContainingIgnoreCaseOrCustomerCccdContaining(String fullname, String cccd, Pageable pageable);

    @Query("SELECT c FROM Contract c WHERE c.createdBy = :createdBy " +
            "AND (LOWER(c.customer.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR c.customer.cccd LIKE CONCAT('%', :keyword, '%'))")
    Page<Contract> findByCreatedByAndKeyword(
            @Param("createdBy") User createdBy,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    Page<Contract> findByCreatedBy(User createdBy, Pageable pageable);

    boolean existsByIdAndCreatedByUsername(Long contractId, String username);
}


