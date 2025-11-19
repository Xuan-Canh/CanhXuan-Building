package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.InvoiceRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    ApiResponse<List<InvoiceResponse>> getAll();
    ApiResponse<InvoiceResponse> getById(Long id);
    ApiResponse<InvoiceResponse> create(InvoiceRequest createInvoiceRequest);
    ApiResponse<InvoiceResponse> update(Long id, InvoiceRequest createInvoiceRequest);
    ApiResponse<Void> delete(Long id);
}
