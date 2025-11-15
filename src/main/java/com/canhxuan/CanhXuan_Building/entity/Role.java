//package com.canhxuan.CanhXuan_Building.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//
//import java.util.Set;
//
//@Entity
//@Table(name = "roles")
//@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@AllArgsConstructor
//@NoArgsConstructor
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    String name;
//
//    @ManyToMany(mappedBy = "roles")
//    Set<User> users;
//}
