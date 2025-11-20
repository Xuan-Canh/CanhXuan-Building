package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    ApiResponse<Page<User>> findAll(Integer page);

    ApiResponse<User> getById(Long id);

    ApiResponse<User> create(User user);

    ApiResponse<User> update(Long id, User user);

    ApiResponse<Void> delete(Long id);
}
