package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
import com.canhxuan.CanhXuan_Building.repository.DashboardRepository;
import com.canhxuan.CanhXuan_Building.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public ApiResponse<DashboardDto> getDashboard() {
        DashboardDto dashboardData = dashboardRepository.getDashboard();
        if (dashboardData != null) {
            return new ApiResponse<>(true, "Dashboard data retrieved successfully", null, dashboardData);
        }
        return null;
    }
}
