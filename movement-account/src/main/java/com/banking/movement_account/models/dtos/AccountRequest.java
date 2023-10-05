package com.banking.movement_account.models.dtos;

import com.banking.movement_account.models.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountRequest {

    @NotNull(message = "El cliente no puede estar en blanco")
    @Positive(message = "El cliente debe ser un valor positivo")
    private Long customer_id;

    @NotNull(message = "El número de cuenta no puede estar en blanco")
    private Integer account_number;

    @NotNull(message = "El tipo de cuenta no puede estar en blanco")
    private AccountType account_type;

    private Double balance;

}