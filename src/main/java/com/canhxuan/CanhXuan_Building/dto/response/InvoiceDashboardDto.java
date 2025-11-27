package com.canhxuan.CanhXuan_Building.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDashboardDto {
    Double monthlyRevenue;
    Long unpaidInvoices;
}
