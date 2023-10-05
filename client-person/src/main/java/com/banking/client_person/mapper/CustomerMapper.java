package com.banking.client_person.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.banking.client_person.models.dtos.CustomerResponse;
import com.banking.client_person.models.entities.Customer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    /**
     * Converts a Customer object to a CustomerResponseDto object.
     * 
     * @param cust The Customer object to be converted.
     * @return A CustomerResponseDto object representing the converted customer
     *         information.
     */
    public CustomerResponse mapToCustomerResponse(Customer cust) {
        return CustomerResponse.builder()
                .customer_id(cust.getCustomerId())
                .name(cust.getPerson().getName())
                .gender(cust.getPerson().getGender().toString())
                .age(cust.getPerson().getAge())
                .identification(cust.getPerson().getIdentification())
                .password(cust.getPassword())
                .address(cust.getPerson().getAddress())
                .phone(cust.getPerson().getPhone())
                .status(cust.getStatus())
                .build();
    }

    /**
     * Converts a list of Customer objects to a list of CustomerResponseDto objects.
     * 
     * @param custs The list of Customer objects to be converted.
     * @return A list of CustomerResponseDto objects representing the converted
     *         customer information.
     */
    public List<CustomerResponse> mapToCustomerResponseList(List<Customer> custs) {
        return custs.stream().map(this::mapToCustomerResponse).toList();
    }

}