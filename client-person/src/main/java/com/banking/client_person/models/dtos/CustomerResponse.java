package com.banking.client_person.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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