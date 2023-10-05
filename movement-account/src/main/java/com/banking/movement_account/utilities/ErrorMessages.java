package com.banking.movement_account.utilities;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {
    
    public final String ACCOUNT_NOT_FOUND = "No se encontró ninguna cuenta con este ID.";
    public final String UNAVAILABLE_BALANCE = "No es posible realizar un retiro de esta cantidad, ya que la cuenta tiene un saldo máximo de ";
    public final String FROM_PARAMETER_REQUIRED = "El parametro from es requerido.";
    public final String TO_PARAMETER_REQUIRED = "El parametro to es requerido.";
    public final String ACCOUNT_DEACTIVATED = "Esta cuenta está desactivada.";
}