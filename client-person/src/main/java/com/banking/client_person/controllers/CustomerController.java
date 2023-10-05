package com.banking.client_person.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.banking.client_person.exceptionHandler.ErrorResponse;
import com.banking.client_person.mapper.CustomerMapper;
import com.banking.client_person.models.dtos.*;
import com.banking.client_person.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService custServ;

    /**
     * Creates a new customer.
     * 
     * @param custReq The request body containing customer information.
     * @return A ResponseEntity with HTTP status 201 (Created) on success.
     *         Returns an error response with HTTP status 400 (Bad Request) if an
     *         exception occurs.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerRequest custReq) {
        try {
            custServ.create(custReq);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            e.getMessage()));
        }
    }

    /**
     * Retrieves a list of customers.
     * 
     * @return A ResponseEntity containing a list of CustomerResponse objects with
     *         HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(new CustomerMapper().mapToCustomerResponseList(custServ.findAll()));
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param id The ID of the customer to retrieve.
     * @return A ResponseEntity with the retrieved CustomerResponse object and HTTP
     *         status 200 (OK) if found.
     *         Returns HTTP status 404 (Not Found) if the customer with the
     *         specified ID does not exist.
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        var cust = custServ.findById(id);
        if (cust == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new CustomerMapper().mapToCustomerResponse(cust));
    }

    /**
     * Updates a customer by their ID.
     * 
     * @param id      The ID of the customer to update.
     * @param custDto The request body containing updated customer information.
     * @return A ResponseEntity with the updated customer details and HTTP status
     *         200 (OK) on success.
     *         Returns HTTP status 404 (Not Found) if the customer with the
     *         specified ID does not exist.
     */
    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CustomerRequest custDto) {
        try {
            return ResponseEntity.ok(custServ.update(id, custDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a customer by their ID.
     * 
     * @param id The ID of the customer to delete.
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        custServ.delete(id);
    }

}
