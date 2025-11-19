package com.canhxuan.CanhXuan_Building.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest {
    Long contractId;
    LocalDate invoiceDate;
    LocalDate dueDate;
    List<ServiceUsageDetail> serviceUsageDetails;
    String note;
}
