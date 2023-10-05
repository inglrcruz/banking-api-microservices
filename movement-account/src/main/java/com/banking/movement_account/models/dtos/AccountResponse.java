package com.banking.movement_account.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    private Long account_id;
    private Integer account_number;
    private String account_type;
    private String balance;
    private Long customer_id;
    private Boolean status;

}