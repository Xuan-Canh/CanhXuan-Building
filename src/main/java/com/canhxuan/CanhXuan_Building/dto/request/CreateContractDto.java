package com.canhxuan.CanhXuan_Building.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateContractDto {
    Long customerId;
    Long roomId;
    LocalDate startDate;
    LocalDate endDate;
    double depositAmount;
    double monthlyRent;
    int paymentDueDate;
    String note;
}
