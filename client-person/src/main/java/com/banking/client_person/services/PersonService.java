package com.banking.client_person.services;

import org.springframework.stereotype.Service;
import com.banking.client_person.models.dtos.CustomerRequest;
import com.banking.client_person.models.entities.Person;
import com.banking.client_person.repositories.PersonsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonsRepository perRepo;

    /**
     * Creates a new person.
     * 
     * @param custDto The request containing person information.
     * @return The newly created Person object.
     */
    public Person create(CustomerRequest custDto) {
        var per = Person.builder()
                .age(custDto.getAge())
                .gender(custDto.getGender())
                .address(custDto.getAddress())
                .name(custDto.getName())
                .phone(custDto.getPhone())
                .identification(custDto.getIdentification())
                .build();
        return perRepo.save(per);
    }

    /**
     * Retrieves a person by their ID.
     * 
     * @param id The ID of the person to retrieve.
     * @return The Person object with the specified ID or null if not found.
     */
    public Person findById(Long id) {
        return perRepo.findById(id).orElse(null);
    }

    /**
     * Finds a person by their identification.
     * 
     * @param id The identification of the person to find.
     * @return The Person object with the specified identification or null if not
     *         found.
     */
    public Person findByIdentification(String id) {
        return perRepo.findByIdentification(id);
    }

    /**
     * Updates a person by their ID.
     * 
     * @param id      The ID of the person to update.
     * @param custDto The request containing updated person information.
     * @return The updated Person object.
     */
    public Person update(Long id, CustomerRequest custDto) {
        Person exisPers = findById(id);
        if (custDto.getAge() != null)
            exisPers.setAge(custDto.getAge());
        if (custDto.getGender() != null)
            exisPers.setGender(custDto.getGender());
        if (custDto.getAddress() != null)
            exisPers.setAddress(custDto.getAddress());
        if (custDto.getName() != null)
            exisPers.setName(custDto.getName());
        if (custDto.getPhone() != null)
            exisPers.setPhone(custDto.getPhone());
        if (custDto.getIdentification() != null && !custDto.getIdentification().equals(exisPers.getIdentification()))
            exisPers.setIdentification(custDto.getIdentification());
        return perRepo.save(exisPers);
    }

}