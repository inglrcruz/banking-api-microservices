package com.banking.movement_account.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.banking.movement_account.models.dtos.*;
import com.banking.movement_account.models.entities.Account;
import com.banking.movement_account.repositories.AccountsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountsRepository acctRepo;

    /**
     * Creates a new account based on the provided account request data.
     * 
     * @param acctReq The request object containing account details.
     * @return The newly created Account object.
     */
    public Account create(AccountRequest acctReq) {
        var acct = Account.builder()
                .accountNumber(acctReq.getAccount_number())
                .accountType(acctReq.getAccount_type())
                .balance(acctReq.getBalance())
                .customerId(acctReq.getCustomer_id())
                .status(true)
                .build();
        return acctRepo.save(acct);
    }

    /**
     * Retrieves a list of all accounts.
     * 
     * @return A list of Account objects.
     */
    public List<Account> findAll() {
        return acctRepo.findAll();
    }

    /**
     * Retrieves an account by its unique ID.
     * 
     * @param id The ID of the account to retrieve.
     * @return The Account object if found, or null if not found.
     */
    public Account findById(Long id) {
        return acctRepo.findById(id).orElse(null);
    }

    /**
     * Updates an existing account with the provided account request data.
     * 
     * @param id      The ID of the account to update.
     * @param acctReq The request object containing updated account details.
     * @return The updated Account object.
     */
    public Account update(Long id, AccountRequest acctReq) {
        Account exisAcct = this.findById(id);
        if (exisAcct != null) {
            if (acctReq.getAccount_number() != null && exisAcct.getAccountNumber() != acctReq.getAccount_number())
                exisAcct.setAccountNumber(acctReq.getAccount_number());
            if (acctReq.getAccount_type() != null)
                exisAcct.setAccountType(acctReq.getAccount_type());
            if (acctReq.getBalance() != null)
                exisAcct.setBalance(acctReq.getBalance());
            return acctRepo.save(exisAcct);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Deletes an account by its unique ID.
     * 
     * @param id The ID of the account to delete.
     */
    public void delete(Long id) {
        acctRepo.deleteById(id);
    }

    /**
     * Deletes all accounts associated with a specific customer by their customer
     * ID.
     * 
     * @param id The customer ID for which associated accounts should be deleted.
     */
    public void deleteByCustomer(Long id) {
        List<Account> custList = acctRepo.findAllByCustomerId(id);
        acctRepo.deleteAll(custList);
    }

}