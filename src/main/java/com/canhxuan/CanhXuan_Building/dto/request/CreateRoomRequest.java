package com.canhxuan.CanhXuan_Building.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateRoomRequest {
    Long id;
    String name;
    int floor;
    int capacity;
    double price;
    String status;
    String description;
    List<String> imageUrls;
    Long buildingId;
}
