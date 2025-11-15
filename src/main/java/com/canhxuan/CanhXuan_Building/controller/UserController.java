package com.canhxuan.CanhXuan_Building.controller;

import com.canhxuan.CanhXuan_Building.entity.User;
import com.canhxuan.CanhXuan_Building.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canhxuan/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user) {
        return ResponseEntity.ok("Update feature has not been implemented yet");
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
