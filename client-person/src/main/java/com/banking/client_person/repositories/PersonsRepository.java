package com.banking.client_person.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.client_person.models.entities.Person;

public interface PersonsRepository extends JpaRepository<Person, Long> {

    Person findByIdentification(String identification);
}