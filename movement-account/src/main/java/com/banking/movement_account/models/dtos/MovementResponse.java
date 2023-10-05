package com.banking.movement_account.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementResponse {

    private Long movement_id;
    private Integer account_number;
    private String account_type;
    private String motion;
    private String initial_balance;
    private String balance;
    private String date;
    private Boolean status;

}