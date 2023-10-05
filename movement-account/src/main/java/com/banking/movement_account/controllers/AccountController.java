package com.banking.movement_account.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import com.banking.movement_account.exceptionHandler.ErrorResponse;
import com.banking.movement_account.mapper.AccountMapper;
import com.banking.movement_account.models.dtos.*;
import com.banking.movement_account.models.entities.Account;
import com.banking.movement_account.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService acctServ;
    private final MovementService movServ;
    private final WebClient.Builder webClientBuilder;

    /**
     * Create a new account.
     * 
     * @param acctReq The request containing account data.
     * @return A response entity indicating success or failure.
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody AccountRequest acctReq) {
        try {
            if (this.checkCustomer(acctReq.getCustomer_id())) {
                // Create a new account
                var acct = acctServ.create(acctReq);
                // Create an initial credit movement for the account
                MovementRequest movReq = new MovementRequest();
                movReq.setAccount_id(acct.getAccountId());
                movReq.setValue(acct.getBalance());
                movServ.createNew(acct, movReq);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            e.getMessage()));
        }
    }

    /**
     * Checks if a customer exists based on the provided customer ID.
     *
     * @param customer_id The customer ID to check.
     * @return True if the customer exists; false otherwise.
     */
    private Boolean checkCustomer(Long customer_id) {
        try {
            this.webClientBuilder.build()
                    .get()
                    .uri("lb://client-person/api/clientes/" + customer_id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return a list of accounts.
     * 
     * @return A response entity containing a list of accounts.
     */
    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.ok(new AccountMapper().mapToAccountResponseList(acctServ.findAll()));
    }

    /**
     * Return an account by its unique ID.
     * 
     * @return A response entity containing the requested account or a not found
     *         response.
     */
    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        Account acct = acctServ.findById(id);
        if (acct != null) {
            return ResponseEntity.ok(new AccountMapper().mapToAccountResponse(acct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update an account by its unique ID.
     * 
     * @param id      The ID of the account to update.
     * @param acctReq The request containing updated account data.
     * @return A response entity containing the updated account or a not found
     *         response.
     */
    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AccountRequest acctReq) {
        try {
            Account acct = acctServ.findById(id);
            if (acct != null) {
                acctServ.update(acctReq, acct);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an account by its unique ID.
     * 
     * @param id The ID of the account to delete.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (acctServ.findById(id) != null) {
            acctServ.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
