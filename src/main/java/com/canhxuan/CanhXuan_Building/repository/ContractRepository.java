package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Page<Contract> findByCustomerFullnameContainingIgnoreCaseOrCustomerCccdContaining(String fullname, String cccd, Pageable pageable);

    Page<Contract> findByCreatedBy(User createdBy, Pageable pageable);
}


