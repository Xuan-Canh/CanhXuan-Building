package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.request.LoginRequest;
import com.canhxuan.CanhXuan_Building.dto.request.MailDto;
import com.canhxuan.CanhXuan_Building.dto.request.RegisterRequest;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.dto.response.LoginResponse;
import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import com.canhxuan.CanhXuan_Building.service.AuthService;
import com.canhxuan.CanhXuan_Building.utils.JwtUtil;
import com.canhxuan.CanhXuan_Building.utils.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final EmailService emailService;
    private final Producer producer;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RedisService redisService, EmailService emailService, Producer producer) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.emailService = emailService;
        this.producer = producer;
    }


    @Override
    public ApiResponse<Void> register(RegisterRequest request) {
        ApiResponse<Void> response = new ApiResponse<>();
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
//        user.setRole("ADMIN");
        userRepository.save(user);
        response.setSuccess(true);
        response.setMessage("Register successfully");
        return response;
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String accessToken = jwtUtil.generateAccessToken(authentication);
            String refreshToken = jwtUtil.generateRefreshToken(authentication);
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setRole(roles);
            loginResponse.setUsername(request.getUsername().toLowerCase());
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(refreshToken);
            User user = userRepository.findByUsername(request.getUsername()).get();
            loginResponse.setUserAvatar(user.getAvatarUrl());
            response.setSuccess(true);
            response.setMessage("Login successfully, welcome " + request.getUsername());
            response.setData(loginResponse);
            return response;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public ApiResponse<Void> logout(String token) {
        ApiResponse<Void> response = new ApiResponse<>();
        redisService.blackListToken(token, 15);
        response.setSuccess(true);
        response.setMessage("Logout successfully");
        return response;
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token is invalid");
        }
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateAccessTokenByRefreshToken(refreshToken);
        return Map.of("accessToken", newAccessToken);
    }

    @Override
    public ApiResponse<Void> forgotPassword(String email) throws JsonProcessingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));
        String token = UUID.randomUUID().toString();
        redisService.saveToRedis(token, user.getUsername(), 15);
        String resetLink = "http://localhost:8080/canhxuan/auth/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String body = "Dear " + user.getUsername() + ",\n\n" +
                "We received a request to reset your password. Please click the link below to reset your password:\n" +
                resetLink + "\n\n" +
                "If you did not request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Canh Xuan Building Team";
        MailDto mailDto = new MailDto(email, subject, body);
        String message = new ObjectMapper().writeValueAsString(mailDto);
        producer.send("auth-topic", message);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Password reset link has been sent to your email");
        return response;
    }

    @Override
    public Map<String, String> resetPassword(String token) {
        String username = redisService.getValue(token);
        if (username == null) {
            throw new RuntimeException("Invalid or expired token");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        redisService.deleteFromRedis(token);
        return Map.of("message", "Your password has been reset. Your new password is: " + newPassword);
    }


}
