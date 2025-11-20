package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;

public interface DashboardService {

    public ApiResponse<DashboardDto> getDashboard();
}
