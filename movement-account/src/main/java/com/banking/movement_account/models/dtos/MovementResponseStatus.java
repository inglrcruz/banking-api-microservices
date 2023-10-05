package com.banking.movement_account.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementResponseStatus {

    private String date;
    private String account_number;
    private String account_type;
    private String initial_balance;
    private String status;
    private String motion;
    private String balance;

}