package com.canhxuan.CanhXuan_Building.repository;

import com.canhxuan.CanhXuan_Building.dto.response.DashboardDto;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceDashboardDto;

public interface DashboardRepository {
    DashboardDto getDashboard();
    InvoiceDashboardDto getInvoiceDashboard();
}
