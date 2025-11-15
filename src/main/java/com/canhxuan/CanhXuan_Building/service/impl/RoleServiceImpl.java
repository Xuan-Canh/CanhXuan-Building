//package com.canhxuan.CanhXuan_Building.service.impl;
//
//import com.canhxuan.CanhXuan_Building.entity.Role;
//import com.canhxuan.CanhXuan_Building.repository.RoleRepository;
//import com.canhxuan.CanhXuan_Building.service.RoleService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    private final RoleRepository roleRepository;
//
//    public RoleServiceImpl(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public List<Role> getAll() {
//        return roleRepository.findAll();
//    }
//
//    @Override
//    public Role getById(Integer id) {
//        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
//    }
//
//    @Override
//    public Role create(Role role) {
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public String delete(Integer id) {
//        roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
//        roleRepository.deleteById(id);
//        return "delete successfully";
//    }
//}
