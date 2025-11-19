package com.canhxuan.CanhXuan_Building.dto.response;

import com.canhxuan.CanhXuan_Building.entity.Contract;
import com.canhxuan.CanhXuan_Building.entity.InvoiceServiceDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InvoiceResponse {
    Long id;
    Contract contract;
    LocalDate invoiceDate;
    LocalDate dueDate;
    Double roomRent;
    Double totalServiceFee;
    Double totalAmount;
    String status;
    String note;
    LocalDateTime paidAt;
    List<InvoiceServiceDetail> serviceDetail;
}
