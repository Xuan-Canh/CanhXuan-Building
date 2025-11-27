package com.canhxuan.CanhXuan_Building.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    Long totalBuildings;
    Long totalRooms;
    Long emptyRooms;
    Long rentedRooms;
    Long totalCustomers;
    Long activeContracts;
    Double monthlyRevenue;
    Long unpaidInvoices;
}
