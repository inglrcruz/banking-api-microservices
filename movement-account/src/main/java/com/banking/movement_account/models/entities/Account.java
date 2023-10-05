package com.banking.movement_account.models.entities;

import java.io.Serializable;
import java.util.List;

import com.banking.movement_account.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "accounts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "account_number", nullable = false, unique = true)
    private Integer accountNumber;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    AccountType accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private Boolean status;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Movement> movement;

}