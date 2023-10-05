package com.banking.movement_account.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovementRequest {

    @NotNull(message = "La cuenta no puede estar en blanco")
    @Positive(message = "La cuenta debe ser un valor positivo")
    private Long account_id;

    @NotNull(message = "La cuenta no puede estar en blanco")
    private Double value;

}