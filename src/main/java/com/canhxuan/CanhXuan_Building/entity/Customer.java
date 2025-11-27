package com.canhxuan.CanhXuan_Building.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "customers", indexes ={
        @Index(name = "idx_customer_fullname", columnList = "fullname"),
        @Index(name = "idx_customer_cccd", columnList = "cccd")
})
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @Size(min = 3, max = 100, message = "Fullname must be between 3 and 100 characters")
    String fullname;

    @Pattern(regexp = "^[0-9]{9,12}$", message = "CCCD must be between 9 and 12 digits")
    @Column(unique = true)
    String cccd;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Phone number must be between 10 and 11 digits")
    String phone;

    @Email
    String email;

    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    String address;

    @Enumerated(EnumType.STRING)
    CustomerStatus status;

    String gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    java.util.List<Contract> contracts;
}
