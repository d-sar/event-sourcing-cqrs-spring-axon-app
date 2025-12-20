package com.enset.eventsourcingcqrs.query.hendlers;

import com.enset.eventsourcingcqrs.commands.events.*;
import com.enset.eventsourcingcqrs.query.entities.Account;
import com.enset.eventsourcingcqrs.query.entities.AccountOperation;
import com.enset.eventsourcingcqrs.query.entities.OperationType;
import com.enset.eventsourcingcqrs.query.repo.AccountRepo;
import com.enset.eventsourcingcqrs.query.repo.OperationRepo;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountEventHandler {
    private AccountRepo accountRepo;
    private OperationRepo operationRepo;

    public AccountEventHandler(AccountRepo accountRepo, OperationRepo operationRepo) {
        this.accountRepo = accountRepo;
        this.operationRepo = operationRepo;
    }
    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage) {
       log.info("-------------Query Side AccountCreatedEvent Received------------------");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .currency(event.getCurrency())
                .build();
        accountRepo.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("-------------Query Side AccountActivatedEvent Received------------------");
        Account account = accountRepo.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountStatusUpdatedEvent event) {
        log.info("-------------Query Side AccountStatusUpdatedEvent Received------------------");
        Account account = accountRepo.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage) {
        log.info("-------------Query Side AccountDebitedEvent Received------------------");
        Account account = accountRepo.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepo.save(accountOperation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepo.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage) {
        log.info("-------------Query Side AccountCreditedEvent Received------------------");
        Account account = accountRepo.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .date(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepo.save(accountOperation);
        account.setBalance(account.getBalance()+ event.getAmount());
        accountRepo.save(account);
    }

}
