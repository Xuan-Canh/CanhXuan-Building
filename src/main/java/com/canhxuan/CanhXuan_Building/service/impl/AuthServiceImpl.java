package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.request.LoginRequest;
import com.canhxuan.CanhXuan_Building.dto.request.RegisterRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.LoginResponse;
import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import com.canhxuan.CanhXuan_Building.service.AuthService;
import com.canhxuan.CanhXuan_Building.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
    }


    @Override
    public String register(RegisterRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            return "Username already exists, please try another username";
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        userRepository.save(user);
        return "Register successfully";
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String accessToken = jwtUtil.generateAccessToken(authentication);
            String refreshToken = jwtUtil.generateRefreshToken(authentication);
            ApiResponse<LoginResponse> response = new ApiResponse<>();
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUsername(request.getUsername());
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(refreshToken);
            response.setMessage("Login successfully, welcome " + request.getUsername());
            response.setData(loginResponse);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public ApiResponse<Void> logout(String token) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setMessage("Logout successfully");
        redisService.blackListToken(token, 15);
        return response;
    }
}
