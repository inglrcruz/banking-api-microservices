package com.banking.client_person.services;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.banking.client_person.models.dtos.CustomerRequest;
import com.banking.client_person.models.entities.*;
import com.banking.client_person.repositories.CustomersRepository;
import com.banking.client_person.utilities.ErrorMessages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomersRepository custRepo;
    private final PersonService perSev;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Adds a new customer.
     * 
     * @param custReq The request containing customer information.
     * @return The ID of the newly created customer.
     */
    public Long create(CustomerRequest custReq) {
        if (perSev.findByIdentification(custReq.getIdentification()) != null)
            throw new RuntimeException(new ErrorMessages().CLIENT_ID_ALREADY_EXISTS);
        var cust = Customer.builder()
                .password(custReq.getPassword())
                .person(perSev.create(custReq))
                .status(true)
                .build();
        return custRepo.save(cust).getCustomerId();
    }

    /**
     * Retrieves a list of all customers.
     * 
     * @return A list of Customer objects representing all customers.
     */
    public List<Customer> findAll() {
        return custRepo.findAll();
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param id The ID of the customer to retrieve.
     * @return The Customer object with the specified ID or null if not found.
     */
    public Customer findById(Long id) {
        return custRepo.findById(id).orElse(null);
    }

    /**
     * Updates a customer by their ID.
     * 
     * @param id      The ID of the customer to update.
     * @param custDto The request containing updated customer information.
     * @return The updated Customer object.
     * @throws NoSuchElementException if the customer with the specified ID does not
     *                                exist.
     */
    public Customer update(Long id, CustomerRequest custDto) {
        Customer exisCust = this.findById(id);
        if (exisCust != null) {
            if (custDto.getPassword() != null)
                exisCust.setPassword(custDto.getPassword());
            Person prs = perSev.update(id, custDto);
            if (prs != null)
                exisCust.setPerson(prs);
            return custRepo.save(exisCust);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Deletes a customer by their ID and sends a notification to Kafka.
     * 
     * @param id The ID of the customer to delete.
     */
    public void delete(Long id) {
        custRepo.deleteById(id);
        this.kafkaTemplate.send("customer-delete-topic", id.toString());
    }

}