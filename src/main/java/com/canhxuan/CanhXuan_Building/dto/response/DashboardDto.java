package com.canhxuan.CanhXuan_Building.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DashboardDto {
    Long totalBuildings;
    Long totalRooms;
    Long emptyRooms;
    Long rentedRooms;
    Long totalCustomers;
    Long activeContracts;
}
