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
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    @Override
    public List<Building> getByName(String name) {
        return buildingRepository.findByName(name);
    }

    @Override
    public Building getById(Long id) {
        return buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
    }

    @Override
    public Building create(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public Building update(Long id, Building building) {
        Building presentBuilding = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found with id: " + id));
        presentBuilding.setName(building.getName());
        presentBuilding.setAddress(building.getAddress());
        presentBuilding.setDescription(building.getDescription());
        presentBuilding.setApartments(building.getApartments());
        presentBuilding.setFloors(building.getFloors());
        return buildingRepository.save(presentBuilding);
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
