package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
