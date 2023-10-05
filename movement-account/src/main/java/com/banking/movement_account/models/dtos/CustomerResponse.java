package com.banking.movement_account.models.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CustomerResponse {

    private Long customer_id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String password;
    private String address;
    private String phone;
    private boolean status;

}