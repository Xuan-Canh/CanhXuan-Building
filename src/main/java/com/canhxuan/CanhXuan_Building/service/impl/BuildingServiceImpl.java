package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;
import com.canhxuan.CanhXuan_Building.repository.BuildingRepository;
import com.canhxuan.CanhXuan_Building.service.BuildingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;


    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public ApiResponse<List<Building>> getAll() {
        ApiResponse<List<Building>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get all buildings successfully");
        apiResponse.setData(buildingRepository.findAll());
        return apiResponse;
    }

    @Override
    public ApiResponse<List<Building>> getByName(String name) {
        ApiResponse<List<Building>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get buildings by name successfully");
        apiResponse.setData(buildingRepository.findByName(name));
        return apiResponse;
    }

    @Override
    public ApiResponse<Building> getById(Long id) {
        ApiResponse<Building> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get building by id successfully");
        apiResponse.setData(buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id)));
        return apiResponse;
    }

    @Override
    public ApiResponse<Building> create(Building building) {
        ApiResponse<Building> apiResponse = new ApiResponse<>();
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
        apiResponse.setMessage("Update building successfully");
        apiResponse.setData(buildingRepository.save(presentBuilding));
        return apiResponse;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        ApiResponse<Void> response = new ApiResponse<>();
        buildingRepository.delete(building);
        response.setMessage("Delete building successfully");
        return response;
    }
}
