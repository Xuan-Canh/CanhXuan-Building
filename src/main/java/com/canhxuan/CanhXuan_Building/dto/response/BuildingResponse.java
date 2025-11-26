package com.canhxuan.CanhXuan_Building.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BuildingResponse {
    Long id;
    String name;
    String address;
    String description;
    Integer floors;
    Integer rooms;
    List<ImageDto> images;
}
