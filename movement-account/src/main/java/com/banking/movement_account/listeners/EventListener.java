package com.banking.movement_account.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.banking.movement_account.services.AccountService;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    private final AccountService accServ;

    @KafkaListener(topics = "customer-delete-topic")
    public void handleOrdersNotifications(String id) {
        Long nId = Long.valueOf(id);
        accServ.deleteByCustomer(nId);
        log.info("Delete movement and account by customer ID: " + nId);
    }
}