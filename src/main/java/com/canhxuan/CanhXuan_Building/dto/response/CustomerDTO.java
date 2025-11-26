package com.canhxuan.CanhXuan_Building.dto.response;

import com.canhxuan.CanhXuan_Building.entity.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String fullname;
    private String cccd;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private CustomerStatus status;
    private String gender;
}