package com.banking.client_person.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.client_person.models.entities.Customer;

public interface CustomersRepository extends JpaRepository<Customer, Long> {
}