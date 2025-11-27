package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
import com.canhxuan.CanhXuan_Building.dto.response.DashboardResponse;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceDashboardDto;
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
    public ApiResponse<DashboardResponse> getDashboard() {
        DashboardDto dashboardData = dashboardRepository.getDashboard();
        InvoiceDashboardDto invoiceDashboardData = dashboardRepository.getInvoiceDashboard();
        if (dashboardData != null && invoiceDashboardData != null) {
            DashboardResponse response = new DashboardResponse();
            response.setTotalBuildings(dashboardData.getTotalBuildings());
            response.setTotalRooms(dashboardData.getTotalRooms());
            response.setEmptyRooms(dashboardData.getEmptyRooms());
            response.setRentedRooms(dashboardData.getRentedRooms());
            response.setTotalCustomers(dashboardData.getTotalCustomers());
            response.setActiveContracts(dashboardData.getActiveContracts());
            response.setMonthlyRevenue(invoiceDashboardData.getMonthlyRevenue());
            response.setUnpaidInvoices(invoiceDashboardData.getUnpaidInvoices());
            return new ApiResponse<>(true, "Dashboard data retrieved successfully", null, response);
        }
        return null;
    }
}
