package com.banking.movement_account.utilities;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import com.banking.movement_account.models.enums.AccountType;

public class Format {

    /**
     * Formats a timestamp using the specified pattern.
     *
     * @param date    The timestamp to be formatted.
     * @param pattern The date pattern to use for formatting.
     * @return A string representing the formatted date.
     */
    public String dateFormat(Timestamp date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    /**
     * Formats a double number according to the default locale settings.
     *
     * @param number The number to be formatted.
     * @return A string representing the formatted number.
     */
    public String numberFormat(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }

    /**
     * Determines the type of financial movement based on the given number.
     * If the number is greater than zero, it's considered a deposit.
     * If the number is less than or equal to zero, it's considered a withdrawal.
     *
     * @param number The numeric value to determine the movement type.
     * @return A string representing the movement type, e.g., "Deposit of 100" or
     *         "Withdrawal of 50".
     */
    public String movementType(double number) {
        String movType;
        if (number > 0) {
            movType = "Deposito de " + numberFormat(number);
        } else {
            movType = "Retiro de " + numberFormat(-number);
        }
        return movType;
    }

    /**
     * Converts an account type enumeration into a human-readable account type.
     *
     * @param accType The account type enumeration to be converted.
     * @return A string representing the human-readable account type, e.g.,
     *         "Savings" or "Current".
     */
    public String accountType(AccountType accType) {
        return (accType.equals(AccountType.savings_account)) ? "Ahorros" : "Corriente";
    }
}
