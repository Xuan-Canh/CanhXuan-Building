package com.canhxuan.CanhXuan_Building.dto.response;

import com.canhxuan.CanhXuan_Building.entity.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private ServiceType type;
    private String unit;
}