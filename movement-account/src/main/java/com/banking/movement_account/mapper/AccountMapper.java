package com.banking.movement_account.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.banking.movement_account.models.dtos.AccountResponse;
import com.banking.movement_account.models.entities.Account;
import com.banking.movement_account.models.enums.AccountType;
import com.banking.movement_account.utilities.Format;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    /**
     * Maps an Account object to an AccountResponse object.
     * 
     * @param acct The Account object to be mapped.
     * @return An AccountResponse object representing the mapped account data.
     */
    public AccountResponse mapToAccountResponse(Account acct) {
        Format format = new Format();
        String accType = (acct.getAccountType().equals(AccountType.savings_account)) ? "Savings" : "Current";
        
        return AccountResponse.builder()
                .account_id(acct.getAccountId())
                .account_number(acct.getAccountNumber())
                .account_type(accType)
                .balance(format.numberFormat(Double.parseDouble(acct.getBalance().toString())))
                .customer_id(acct.getCustomerId())
                .status(acct.getStatus())
                .build();
    }

    /**
     * Maps a list of Account objects to a list of AccountResponse objects.
     * 
     * @param accts The list of Account objects to be mapped.
     * @return A list of AccountResponse objects representing the mapped account
     *         data.
     */
    public List<AccountResponse> mapToAccountResponseList(List<Account> accts) {
        return accts.stream().map(this::mapToAccountResponse).toList();
    }

}