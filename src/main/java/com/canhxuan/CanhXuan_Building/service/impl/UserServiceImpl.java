package com.canhxuan.CanhXuan_Building.service.impl;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.repository.UserRepository;
import com.canhxuan.CanhXuan_Building.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse<Page<User>> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        ApiResponse<Page<User>> response = new ApiResponse<>();
        response.setMessage("Get all users successfully");
        response.setData(userRepository.findAll(pageable));
        return response;
    }

    @Override
    public ApiResponse<User> getById(Long id) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setMessage("Get user successfully");
        response.setData(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
        return response;
    }

    @Override
    public ApiResponse<User> create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ApiResponse<User> response = new ApiResponse<>();
        response.setMessage("Create user successfully");
        response.setData(userRepository.save(user));
        return response;
    }

    @Override
    public ApiResponse<User> update(Long id, User user) {
        ApiResponse<User> response = new ApiResponse<>();
        User presentUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        presentUser.setUsername(user.getUsername());
        presentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        presentUser.setEmail(user.getEmail());
        presentUser.setPhone(user.getPhone());
        presentUser.setRole(user.getRole());
        response.setMessage("Update user successfully");
        response.setData(userRepository.save(presentUser));
        return response;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        ApiResponse<Void> response = new ApiResponse<>();
        response.setMessage("Delete user successfully");
        userRepository.delete(user);
        return response;
    }
}
