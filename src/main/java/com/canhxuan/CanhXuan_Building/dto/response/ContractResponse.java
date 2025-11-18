package com.canhxuan.CanhXuan_Building.dto.response;

import com.canhxuan.CanhXuan_Building.entity.Customer;
import com.canhxuan.CanhXuan_Building.entity.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractResponse {
    Long id;
    Customer customer;
    Room room;
    LocalDate startDate;
    LocalDate endDate;
    double depositAmount;
    double monthlyRent;
    int paymentDueDate;
    String note;
}
