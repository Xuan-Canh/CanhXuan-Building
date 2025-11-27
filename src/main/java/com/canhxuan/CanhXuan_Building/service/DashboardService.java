package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardResponse;

public interface DashboardService {

    public ApiResponse<DashboardResponse> getDashboard();
}
