package com.canhxuan.CanhXuan_Building.dto.request;

import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateContractDto {
    Customer customer;
    Long roomId;
    LocalDate startDate;
    LocalDate endDate;
    double depositAmount;
    double monthlyRent;
    int paymentDueDate;
    String note;
    List<Service> services;
}
