package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BuildingRepository extends JpaRepository<Building,Long> {

    @EntityGraph(value = "Building.images")
    Optional<Building>findById(Long id);

    @EntityGraph(value = "Building.images")
    @Query("select distinct b from Building b")
    Page<Building> findAllWithImages(Pageable pageable);

    @EntityGraph(value = "Building.images")
    Page<Building> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address, Pageable pageable);
}
