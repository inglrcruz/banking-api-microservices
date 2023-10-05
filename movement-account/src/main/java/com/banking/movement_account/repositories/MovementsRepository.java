package com.banking.movement_account.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.banking.movement_account.models.entities.Movement;
import java.util.List;

public interface MovementsRepository extends JpaRepository<Movement, Long> {

    @Query(value = "SELECT date_format(m.date, '%d/%m/%Y') as date, cast(a.account_number as CHAR) as account_number," +
                   " if(a.account_type = 'savings_account', 'Ahorro', 'Corriente') as account_type, m.initial_balance as initial_balance," +
                   " a.status, m.balance as balance, m.value as value " +
                   "FROM movements m " +
                   " INNER JOIN accounts a ON a.account_id = m.account_id AND a.customer_id = :customer " +
                   "WHERE m.date BETWEEN :fromDate AND :toDate ", nativeQuery = true)
    List<Object[]> getListDates(@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("customer") Long customer);

} 