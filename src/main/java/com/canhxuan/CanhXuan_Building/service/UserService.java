package com.canhxuan.CanhXuan_Building.service;

import com.canhxuan.CanhXuan_Building.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User getById(Long id);

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);
}
