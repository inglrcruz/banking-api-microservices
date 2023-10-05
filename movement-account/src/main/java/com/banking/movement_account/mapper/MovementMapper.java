package com.banking.movement_account.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.banking.movement_account.models.dtos.MovementResponse;
import com.banking.movement_account.models.entities.*;
import com.banking.movement_account.utilities.Format;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovementMapper {

    /**
     * Maps a Movement object to a MovementResponse object.
     * 
     * @param movt The Movement object to be mapped.
     * @return A MovementResponse object representing the mapped movement data.
     */
    public MovementResponse mapToMovementResponse(Movement movt) {
        Format fmt = new Format();
        return MovementResponse.builder()
                .movement_id(movt.getMovementId())
                .account_number(movt.getAccount().getAccountNumber())
                .account_type(fmt.accountType(movt.getAccount().getAccountType()))
                .motion(fmt.movementType(movt.getValue()))
                .initial_balance(fmt.numberFormat(movt.getInitialBalance()))
                .balance(fmt.numberFormat(movt.getBalance()))
                .date(fmt.dateFormat(movt.getDate(), "dd/MM/yyyy"))
                .status(movt.getAccount().getStatus())
                .build();
    }

    /**
     * Maps a list of Movement objects to a list of MovementResponse objects.
     * 
     * @param movts The list of Movement objects to be mapped.
     * @return A list of MovementResponse objects representing the mapped movement
     *         data.
     */
    public List<MovementResponse> mapToMovementResponseList(List<Movement> movts) {
        return movts.stream().map(this::mapToMovementResponse).toList();
    }

}