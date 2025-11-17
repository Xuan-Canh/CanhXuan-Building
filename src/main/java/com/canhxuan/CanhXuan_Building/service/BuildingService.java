package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;

import java.util.List;

public interface BuildingService {
    ApiResponse<List<Building>> getAll();
    ApiResponse<List<Building>> getByName(String name);
    ApiResponse<Building> getById(Long id);
    ApiResponse<Building> create(Building building);
    ApiResponse<Building> update(Long id, Building building);
    ApiResponse<Void> delete(Long id);
}
