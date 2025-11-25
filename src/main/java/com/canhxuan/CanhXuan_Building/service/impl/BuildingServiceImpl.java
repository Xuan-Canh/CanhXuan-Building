package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.entity.BuildingImage;
import com.canhxuan.CanhXuan_Building.repository.BuildingImageRepository;
import com.canhxuan.CanhXuan_Building.repository.BuildingRepository;
import com.canhxuan.CanhXuan_Building.service.BuildingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingImageRepository buildingImageRepository;


    public BuildingServiceImpl(BuildingRepository buildingRepository, BuildingImageRepository buildingImageRepository) {
        this.buildingRepository = buildingRepository;
        this.buildingImageRepository = buildingImageRepository;
    }

    @Override
    public ApiResponse<Page<Building>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<Page<Building>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get all buildings successfully");
        apiResponse.setData(buildingRepository.findAll(pageable));
        return apiResponse;
    }

    @Override
    public ApiResponse<Page<Building>> searchByNameOrAddress(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        ApiResponse<Page<Building>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get buildings by name successfully");
        apiResponse.setData(buildingRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable));
        return apiResponse;
    }

    @Override
    public ApiResponse<Building> getById(Long id) {
        ApiResponse<Building> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get building by id successfully");
        apiResponse.setData(buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id)));
        return apiResponse;
    }

    @Override
    public ApiResponse<Building> create(Building building) {
        ApiResponse<Building> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Create building successfully");
        apiResponse.setData(buildingRepository.save(building));
        return apiResponse;
    }

    @Override
    public ApiResponse<Building> update(Long id, Building building) {
        Building presentBuilding = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        presentBuilding.setName(building.getName());
        presentBuilding.setAddress(building.getAddress());
        presentBuilding.setDescription(building.getDescription());
        presentBuilding.setRooms(building.getRooms());
        presentBuilding.setFloors(building.getFloors());
        ApiResponse<Building> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Update building successfully");
        apiResponse.setData(buildingRepository.save(presentBuilding));
        return apiResponse;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(true);
        buildingRepository.delete(building);
        response.setMessage("Delete building successfully");
        return response;
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
        response.setSuccess(true);
        response.setMessage("Save image successfully");
        response.setData(buildingImageRepository.save(buildingImage));
        return response;
    }

    @Override
    public ApiResponse<List<BuildingImage>> findByBuildingId(Long buildingId) {
        ApiResponse response = new ApiResponse<>();
        response.setSuccess(true);
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
            response.setSuccess(true);
            response.setMessage("Delete image successfully");
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + image.getFilePath(), e);
        }
        return response;
    }
}
