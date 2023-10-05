package com.banking.movement_account.models.entities;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "movements")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Long movementId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "account_opening")
    private Boolean account_opening;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Timestamp date;

}