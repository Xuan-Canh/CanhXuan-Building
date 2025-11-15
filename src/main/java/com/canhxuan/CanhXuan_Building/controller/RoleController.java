//package com.canhxuan.CanhXuan_Building.controller;
//
//import com.canhxuan.CanhXuan_Building.entity.Role;
//import com.canhxuan.CanhXuan_Building.service.RoleService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/canhxuan/roles")
//public class RoleController {
//
//    private final RoleService roleService;
//
//    public RoleController(RoleService roleService) {
//        this.roleService = roleService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Role>> getAll() {
//        return ResponseEntity.ok(roleService.getAll());
//    }
//
//    @PostMapping
//    public ResponseEntity<Role> create(@RequestBody Role role) {
//        return ResponseEntity.ok(roleService.create(role));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> update(@RequestBody Integer id) {
//        return ResponseEntity.ok(roleService.delete(id));
//    }
//}
