package com.banking.movement_account.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.movement_account.models.entities.Account;

public interface AccountsRepository extends JpaRepository<Account, Number> {

    Account findByAccountNumber(Integer accountNumber);
    
    List<Account> findAllByCustomerId(Long customerId);

}