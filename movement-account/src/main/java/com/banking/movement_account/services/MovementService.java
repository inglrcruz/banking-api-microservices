package com.banking.movement_account.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.banking.movement_account.models.dtos.*;
import com.banking.movement_account.models.entities.*;
import com.banking.movement_account.repositories.MovementsRepository;
import com.banking.movement_account.utilities.ErrorMessages;
import com.banking.movement_account.utilities.Format;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementsRepository movRepo;
    private final AccountService acctServ;
    private final ErrorMessages errorMsg;

    /**
     * Adds a new movement.
     * 
     * @param acct   The account associated with the movement.
     * @param movDto The movement request data.
     * @return The ID of the newly created movement.
     */
    public Long createNew(Account acct, MovementRequest movDto) {
        var mv = Movement.builder()
                .account(acct)
                .value(movDto.getValue())
                .initialBalance(acct.getBalance())
                .balance(0.0)
                .account_opening(true)
                .build();
        return movRepo.save(mv).getMovementId();
    }

    /**
     * Adds a new movement and updates the account balance.
     * 
     * @param movDto The movement request data.
     * @return The ID of the newly created movement.
     */
    public Long createUpdate(MovementRequest movDto) {
        var acct = this.acctServ.findById(movDto.getAccount_id());
        if (acct == null)
            throw new RuntimeException(errorMsg.ACCOUNT_NOT_FOUND);
        Double balance = 0.0;
        if (movDto.getValue() > 0) {
            balance = acct.getBalance() + movDto.getValue();
        } else {
            if (acct.getBalance() == 0 || acct.getBalance() < -movDto.getValue())
                throw new RuntimeException(errorMsg.UNAVAILABLE_BALANCE + acct.getBalance());
            balance = acct.getBalance() - (-movDto.getValue());
        }
        var mv = Movement.builder()
                .account(acct)
                .value(movDto.getValue())
                .initialBalance(acct.getBalance())
                .balance(balance)
                .account_opening(false)
                .build();
        // Update balance
        AccountRequest acctReq = new AccountRequest();
        acctReq.setBalance(balance);
        this.acctServ.update(acct.getAccountId(), acctReq);
        // Add movement
        return movRepo.save(mv).getMovementId();
    }

    /**
     * Retrieves a list of all movements.
     * 
     * @return A list of Movement objects.
     */
    public List<Movement> findAll() {
        return movRepo.findAll();
    }

    /**
     * Retrieves a movement by its ID.
     * 
     * @param id The ID of the movement to retrieve.
     * @return The Movement object with the specified ID or null if not found.
     */
    public Movement findById(Long id) {
        return movRepo.findById(id).orElse(null);
    }

    /**
     * Retrieves a list of movements within a specified date range.
     * 
     * @param fromDate The start date of the date range.
     * @param toDate   The end date of the date range.
     * @return A list of MovementResponseStatus objects representing movements
     *         within the date range.
     */
    public List<MovementResponseStatus> findListDates(String fromDate, String toDate, Long customer) {
        if (fromDate.isEmpty())
            throw new RuntimeException(errorMsg.FROM_PARAMETER_REQUIRED);
        if (toDate.isEmpty())
            throw new RuntimeException(errorMsg.TO_PARAMETER_REQUIRED);
        var results = movRepo.getListDates(fromDate, toDate, customer);
        List<MovementResponseStatus> movtResp = new ArrayList<>();
        Format format = new Format();
        for (Object[] result : results) {
            movtResp.add(MovementResponseStatus.builder()
                    .date(result[0].toString())
                    .account_number(result[1].toString())
                    .account_type(result[2].toString())
                    .initial_balance(format.numberFormat(Double.parseDouble(result[3].toString())))
                    .status(result[4].toString())
                    .motion(format.movementType(Double.parseDouble(result[6].toString())))
                    .balance(format.numberFormat(Double.parseDouble(result[5].toString())))
                    .build());
        }
        return movtResp;
    }

    /**
     * Deletes a movement by its ID and updates the associated account balance.
     * 
     * @param id The ID of the movement to delete.
     * @param mv The Movement object to be deleted.
     */
    public void delete(Long id, Movement mv) {
        Long acctId = mv.getAccount().getAccountId();
        Account acct = this.acctServ.findById(acctId);
        // Update balance
        Double balance = 0.0;
        if (acct.getBalance() > 0) {
            balance = acct.getBalance() + mv.getValue();
        } else {
            balance = acct.getBalance() - mv.getValue();
        }
        AccountRequest acctDto = new AccountRequest();
        acctDto.setBalance(balance);
        this.acctServ.update(acct.getAccountId(), acctDto);
        // Delete row
        movRepo.deleteById(id);
    }
}