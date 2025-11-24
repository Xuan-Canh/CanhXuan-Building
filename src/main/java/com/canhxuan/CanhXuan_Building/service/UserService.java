package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.dto.request.EditProfileDto;
import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import com.canhxuan.CanhXuan_Building.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    ApiResponse<Page<User>> findAll(Integer page);

    ApiResponse<User> getByUsername(String username);

    ApiResponse<User> create(User user);

    ApiResponse<User> changeAvatar(String username, MultipartFile file) throws IOException;

    ApiResponse<User> update(Long id, User user);

    ApiResponse<User> editProfile(String username, EditProfileDto dto);

    ApiResponse<Void> delete(Long id);
}
