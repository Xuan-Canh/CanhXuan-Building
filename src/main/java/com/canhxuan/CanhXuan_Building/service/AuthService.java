package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.LoginRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.LoginResponse;
import com.canhxuan.CanhXuan_Building.dto.request.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<Void> logout(String token);
}
