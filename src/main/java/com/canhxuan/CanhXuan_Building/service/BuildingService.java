package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BuildingService {
    ApiResponse<Page<Building>> getAll(int page);
    ApiResponse<Page<Building>> searchByNameOrAddress(String keyword, Integer page);
    ApiResponse<Building> getById(Long id);
    ApiResponse<Building> create(Building building);
    ApiResponse<Building> update(Long id, Building building);
    ApiResponse<Void> delete(Long id);
    ApiResponse<BuildingImage> saveImage(Long buildingId, MultipartFile file) throws IOException;
    ApiResponse<List<BuildingImage>> findByBuildingId(Long buildingId);
    ApiResponse<Void> deleteImage(Long imageId);
}
