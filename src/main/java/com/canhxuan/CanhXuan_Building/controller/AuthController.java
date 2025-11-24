package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.dto.request.LoginRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.LoginResponse;
import com.canhxuan.CanhXuan_Building.dto.request.RegisterRequest;
import com.canhxuan.CanhXuan_Building.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/canhxuan/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        return ResponseEntity.ok(authService.logout(request.getHeader("Authorization").substring(7)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> email) throws JsonProcessingException {
        return ResponseEntity.ok(authService.forgotPassword(email.get("email")));
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String token) {
        return ResponseEntity.ok(authService.resetPassword(token));
    }
}
