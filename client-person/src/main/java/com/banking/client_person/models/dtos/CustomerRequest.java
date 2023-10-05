package com.banking.client_person.models.dtos;

import com.banking.client_person.models.enums.GenderType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotNull(message = "El género no puede estar en blanco")
    private GenderType gender;

    @NotNull(message = "La edad no puede estar en blanco")
    @Positive(message = "La edad debe ser un valor positivo")
    private Integer age;

    @NotBlank(message = "La identificación no puede estar en blanco")
    private String identification;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 4, max = 4, message = "La contraseña solo debe tener 4 caracteres.")
    private String password;

    @NotBlank(message = "La dirección no puede estar en blanco")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String address;

    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Size(min = 7, message = "El teléfono debe tener 7 o más dígitos.")
    private String phone;
}
