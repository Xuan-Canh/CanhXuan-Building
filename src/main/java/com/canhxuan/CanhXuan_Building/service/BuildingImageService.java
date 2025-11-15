package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BuildingImageService {
    public ApiResponse<BuildingImage> saveImage(Long buildingId, MultipartFile file) throws IOException;
    public ApiResponse<List<BuildingImage>> findByBuildingId(Long buildingId);
    public ApiResponse<Void> deleteImage(Long imageId);
}
