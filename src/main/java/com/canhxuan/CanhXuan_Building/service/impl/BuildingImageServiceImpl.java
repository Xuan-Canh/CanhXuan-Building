package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import com.canhxuan.CanhXuan_Building.repository.BuildingImageRepository;
import com.canhxuan.CanhXuan_Building.repository.BuildingRepository;
import com.canhxuan.CanhXuan_Building.service.BuildingImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class BuildingImageServiceImpl implements BuildingImageService {

    private final BuildingRepository buildingRepository;
    private final BuildingImageRepository buildingImageRepository;

    public BuildingImageServiceImpl(BuildingRepository buildingRepository, BuildingImageRepository buildingImageRepository) {
        this.buildingRepository = buildingRepository;
        this.buildingImageRepository = buildingImageRepository;
    }

    @Override
    public ApiResponse<BuildingImage> saveImage(Long buildingId, MultipartFile file) throws IOException {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found with id: " + buildingId));
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/images/building/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        BuildingImage buildingImage = new BuildingImage();
        buildingImage.setBuilding(building);
        buildingImage.setFileName(fileName);
        buildingImage.setFilePath(filePath.toString());
        buildingImage.setFileType(file.getContentType());
        ApiResponse<BuildingImage> response = new ApiResponse<>();
        response.setMessage("Save image successfully");
        response.setData(buildingImageRepository.save(buildingImage));
        return response;
    }

    @Override
    public ApiResponse<List<BuildingImage>> findByBuildingId(Long buildingId) {
        ApiResponse response = new ApiResponse<>();
        response.setMessage("Get images successfully");
        response.setData(buildingImageRepository.findByBuildingId(buildingId));
        return response;
    }

    @Override
    public ApiResponse<Void> deleteImage(Long imageId) {
        BuildingImage image = buildingImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
        ApiResponse<Void> response = new ApiResponse<>();

        try {
            Files.deleteIfExists(Paths.get(image.getFilePath()));
            buildingImageRepository.delete(image);
            response.setMessage("Delete image successfully");
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + image.getFilePath(), e);
        }
        return response;
    }
}
