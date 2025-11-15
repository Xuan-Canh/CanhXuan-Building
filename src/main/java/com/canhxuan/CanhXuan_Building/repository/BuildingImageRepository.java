package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingImageRepository extends JpaRepository<BuildingImage, Long> {
    List<BuildingImage> findByBuildingId(Long buildingId);
}
