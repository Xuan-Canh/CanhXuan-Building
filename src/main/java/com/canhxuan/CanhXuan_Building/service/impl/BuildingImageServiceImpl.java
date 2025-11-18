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


}
