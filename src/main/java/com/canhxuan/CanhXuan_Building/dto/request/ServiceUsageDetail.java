package com.canhxuan.CanhXuan_Building.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceUsageDetail {
    Long serviceId;
    Double oldReading;
    Double newReading;
    Double quantity;
}
