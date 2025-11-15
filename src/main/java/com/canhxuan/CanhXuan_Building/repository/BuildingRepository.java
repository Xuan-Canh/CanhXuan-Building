package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BuildingRepository extends JpaRepository<Building,Long> {

    List<Building> findByName(String name);
}
