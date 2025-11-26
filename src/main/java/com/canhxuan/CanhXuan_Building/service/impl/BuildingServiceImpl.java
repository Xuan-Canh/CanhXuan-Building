package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.BuildingResponse;
import com.canhxuan.CanhXuan_Building.dto.response.ImageDto;
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
    public ApiResponse<Page<BuildingResponse>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Building> buildings = buildingRepository.findAllWithImages(pageable);
        Page<BuildingResponse> buildingResponses = buildings.map(building -> {
                    BuildingResponse response = new BuildingResponse();
                    response.setId(building.getId());
                    response.setName(building.getName());
                    response.setAddress(building.getAddress());
                    response.setDescription(building.getDescription());
                    response.setFloors(building.getFloors());
                    response.setRooms(building.getRooms());
                    response.setImages(building.getImages().stream().map(image -> {
                        ImageDto img = new ImageDto();
                        img.setId(image.getId());
                        img.setFileName(image.getFileName());
                        img.setFilePath(image.getFilePath());
                        img.setFileType(image.getFileType());
                        return img;
                    }).toList()
                    );
                    return response;
                });
        ApiResponse<Page<BuildingResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get all buildings successfully");
        apiResponse.setData(buildingResponses);
        return apiResponse;
    }

    @Override
    public ApiResponse<Page<BuildingResponse>> searchByNameOrAddress(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<Building> buildingPages = buildingRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(keyword, keyword, pageable);
        Page<BuildingResponse> buildingResponses = buildingPages.map(building -> {
            BuildingResponse response = new BuildingResponse();
            response.setId(building.getId());
            response.setName(building.getName());
            response.setAddress(building.getAddress());
            response.setDescription(building.getDescription());
            response.setFloors(building.getFloors());
            response.setRooms(building.getRooms());
            response.setImages(building.getImages().stream().map(image -> {
                ImageDto img = new ImageDto();
                img.setId(image.getId());
                img.setFileName(image.getFileName());
                img.setFilePath(image.getFilePath());
                img.setFileType(image.getFileType());
                return img;
            }).toList()
            );
            return response;
        });
        ApiResponse<Page<BuildingResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get buildings by name successfully");
        apiResponse.setData(buildingResponses);
        return apiResponse;
    }

    @Override
    public ApiResponse<BuildingResponse> getById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        BuildingResponse buildingResponse = new BuildingResponse();
        buildingResponse.setId(building.getId());
        buildingResponse.setName(building.getName());
        buildingResponse.setAddress(building.getAddress());
        buildingResponse.setDescription(building.getDescription());
        buildingResponse.setFloors(building.getFloors());
        buildingResponse.setRooms(building.getRooms());
        buildingResponse.setImages(building.getImages().stream().map(image -> {
            ImageDto img = new ImageDto();
            img.setId(image.getId());
            img.setFileName(image.getFileName());
            img.setFilePath(image.getFilePath());
            img.setFileType(image.getFileType());
            return img;
        }).toList()
        );
        ApiResponse<BuildingResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Get building by id successfully");
        apiResponse.setData(buildingResponse);
        return apiResponse;
    }

    @Override
    public ApiResponse<BuildingResponse> create(Building building) {
        Building created = buildingRepository.save(building);
        BuildingResponse buildingResponse = new BuildingResponse();
        buildingResponse.setId(created.getId());
        buildingResponse.setName(created.getName());
        buildingResponse.setAddress(created.getAddress());
        buildingResponse.setDescription(created.getDescription());
        buildingResponse.setFloors(created.getFloors());
        buildingResponse.setRooms(created.getRooms());
        buildingResponse.setImages(created.getImages().stream().map(image -> {
            ImageDto img = new ImageDto();
            img.setId(image.getId());
            img.setFileName(image.getFileName());
            img.setFilePath(image.getFilePath());
            img.setFileType(image.getFileType());
            return img;
        }).toList()
        );
        ApiResponse<BuildingResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Create building successfully");
        apiResponse.setData(buildingResponse);
        return apiResponse;
    }

    @Override
    public ApiResponse<BuildingResponse> update(Long id, Building building) {
        Building presentBuilding = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        presentBuilding.setName(building.getName());
        presentBuilding.setAddress(building.getAddress());
        presentBuilding.setDescription(building.getDescription());
        presentBuilding.setRooms(building.getRooms());
        presentBuilding.setFloors(building.getFloors());
        buildingRepository.save(presentBuilding);
        BuildingResponse buildingResponse = new BuildingResponse();
        buildingResponse.setId(presentBuilding.getId());
        buildingResponse.setName(presentBuilding.getName());
        buildingResponse.setAddress(presentBuilding.getAddress());
        buildingResponse.setDescription(presentBuilding.getDescription());
        buildingResponse.setFloors(presentBuilding.getFloors());
        buildingResponse.setRooms(presentBuilding.getRooms());
        buildingResponse.setImages(presentBuilding.getImages().stream().map(image -> {
            ImageDto img = new ImageDto();
            img.setId(image.getId());
            img.setFileName(image.getFileName());
            img.setFilePath(image.getFilePath());
            img.setFileType(image.getFileType());
            return img;
        }).toList()
        );
        ApiResponse<BuildingResponse> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Update building successfully");
        apiResponse.setData(buildingResponse);
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
    public ApiResponse<ImageDto> saveImage(Long buildingId, MultipartFile file) throws IOException {
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
        buildingImageRepository.save(buildingImage);
        ImageDto imageDto = new ImageDto();
        imageDto.setId(buildingImage.getId());
        imageDto.setFileName(buildingImage.getFileName());
        imageDto.setFilePath(buildingImage.getFilePath());
        imageDto.setFileType(buildingImage.getFileType());
        ApiResponse<ImageDto> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Save image successfully");
        response.setData(imageDto);
        return response;
    }

    @Override
    public ApiResponse<List<ImageDto>> findByBuildingId(Long buildingId) {
        List<ImageDto> imageDtos = buildingImageRepository.findByBuildingId(buildingId).stream().map(
                image -> {
                    ImageDto img = new ImageDto();
                    img.setId(image.getId());
                    img.setFileName(image.getFileName());
                    img.setFilePath(image.getFilePath());
                    img.setFileType(image.getFileType());
                    return img;
                }
        ).toList();
        ApiResponse response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Get images successfully");
        response.setData(imageDtos);
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
