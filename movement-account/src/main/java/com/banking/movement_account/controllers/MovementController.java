package com.banking.movement_account.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banking.movement_account.exceptionHandler.ErrorResponse;
import com.banking.movement_account.mapper.MovementMapper;
import com.banking.movement_account.models.dtos.*;
import com.banking.movement_account.models.entities.Movement;
import com.banking.movement_account.services.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movServ;

    /**
     * Create a new movement.
     * 
     * @param movReq The request containing movement data.
     * @return A response entity indicating success or failure.
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody MovementRequest movReq) {
        try {
            movServ.createUpdate(movReq);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage()));
        }
    }

    /**
     * Retrieve a list of all movements.
     * 
     * @return A response entity containing a list of movements.
     */
    @GetMapping
    public ResponseEntity<List<MovementResponse>> findAll() {
        return ResponseEntity.ok(new MovementMapper().mapToMovementResponseList(movServ.findAll()));
    }

    /**
     * Retrieve a movement by its unique ID.
     * 
     * @param id The ID of the movement to retrieve.
     * @return A response entity containing the requested movement or a not found
     *         response.
     */
    @GetMapping("{id}")
    public ResponseEntity<MovementResponse> findById(@PathVariable Long id) {
        Movement movt = movServ.findById(id);
        if (movt != null) {
            return ResponseEntity.ok(new MovementMapper().mapToMovementResponse(movt));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieve a list of movements within a specified date range.
     * 
     * @param fromDate The start date of the range.
     * @param toDate   The end date of the range.
     * @return A response entity containing a list of movements within the specified
     *         date range.
     */
    @GetMapping("list")
    public ResponseEntity<?> Report(@RequestParam(name = "from") String fromDate,
            @RequestParam(name = "to") String toDate, @RequestParam(name = "customer") Long customer) {
        return ResponseEntity.ok(movServ.findListDates(fromDate, toDate, customer));
    }

    /**
     * Delete a movement by its unique ID.
     * 
     * @param id The ID of the movement to delete.
     * @return A response entity indicating success or failure.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Movement mov = movServ.findById(id);
        if (mov != null) {
            movServ.delete(id, mov);
            return ResponseEntity.ok().body("");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
