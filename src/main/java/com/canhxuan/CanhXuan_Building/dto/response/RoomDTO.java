package com.canhxuan.CanhXuan_Building.dto.response;

import com.canhxuan.CanhXuan_Building.entity.RoomStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String name;
    private Integer floor;
    private Integer capacity;
    private Double price;
    private RoomStatus status;
    private String description;
}