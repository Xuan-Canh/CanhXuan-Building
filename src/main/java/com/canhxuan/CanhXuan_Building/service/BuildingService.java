package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> getAll();
    List<Building> getByName(String name);
    Building getById(Long id);
    Building create(Building building);
    Building update(Long id, Building building);
    ApiResponse<Void> delete(Long id);
}
