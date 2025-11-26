package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.BuildingResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ImageDto;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BuildingService {
    ApiResponse<Page<BuildingResponse>> getAll(int page, int size);
    ApiResponse<Page<BuildingResponse>> searchByNameOrAddress(String keyword, Integer page);
    ApiResponse<BuildingResponse> getById(Long id);
    ApiResponse<BuildingResponse> create(Building building);
    ApiResponse<BuildingResponse> update(Long id, Building building);
    ApiResponse<Void> delete(Long id);
    ApiResponse<ImageDto> saveImage(Long buildingId, MultipartFile file) throws IOException;
    ApiResponse<List<ImageDto>> findByBuildingId(Long buildingId);
    ApiResponse<Void> deleteImage(Long imageId);
}
