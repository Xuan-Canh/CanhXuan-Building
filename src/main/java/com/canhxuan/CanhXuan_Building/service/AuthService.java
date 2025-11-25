package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.LoginRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.LoginResponse;
import com.canhxuan.CanhXuan_Building.dto.request.RegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface AuthService {
    ApiResponse<Void> register(RegisterRequest request);
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<Void> logout(String token);
    Map<String, String> refreshToken(String refreshToken);
    ApiResponse<Void> forgotPassword(String email) throws JsonProcessingException;
    Map<String, String> resetPassword(String token);
}
