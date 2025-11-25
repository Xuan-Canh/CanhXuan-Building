package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingImageRepository extends JpaRepository<BuildingImage, Long> {
    List<BuildingImage> findByBuildingId(Long buildingId);

    @Query(value = "SELECT * FROM building_image WHERE building_id = ?1", nativeQuery = true)
    List<BuildingImage> findByBuildingIdeestsd(Long buildingId);
}
