package com.enset.eventsourcingcqrs.commands.Aggregates;


import com.enset.eventsourcingcqrs.commands.commands.AddAccountCommand;
import com.enset.eventsourcingcqrs.commands.commands.CreditAccountCommand;
import com.enset.eventsourcingcqrs.commands.commands.DebitAccountCommand;
import com.enset.eventsourcingcqrs.commands.commands.UpdateAccountStatusCommand;
import com.enset.eventsourcingcqrs.commands.events.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus  status;
     // pour axon
    public AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("---------------AddAccountCommand Received -----------------");
        if(command.getInitialBalance()<=0) throw new IllegalArgumentException("Initial balance must be greater than zero");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACCTIVATED
        ));
    }

    @CommandHandler
    public void  handle(CreditAccountCommand command) {
        log.info("---------------CreditCommand Received -----------------");
        if(!status.equals(AccountStatus.ACCTIVATED)) throw new RuntimeException("The account"+ command.getId()+"Is not activated");
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }


    @CommandHandler
    public void  handle(DebitAccountCommand command) {
        log.info("---------------DebitAccountCommand Received -----------------");
        if(!status.equals(AccountStatus.ACCTIVATED)) throw new RuntimeException("The account"+ command.getId()+"Is not activated");
        if(balance<command.getAmount()) throw new RuntimeException("balance not sufficient");
        if(command.getAmount()<=0) throw new IllegalArgumentException("Amount must be positive");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @CommandHandler
    public void  handle(UpdateAccountStatusCommand command) {
        log.info("---------------UpdateAccountStatusCommand Received -----------------");
        if(command.getStatus() == status) throw new RuntimeException("this account "+command.getId()+"Is already "+command.getStatus());
        AggregateLifecycle.apply(new AccountStatusUpdatedEvent(
                command.getId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("---------------AccountCreatedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        log.info("---------------AccountCreditedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.balance = this.balance + event.getAmount();

    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("---------------AccountActivatedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        log.info("---------------AccountDebitedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.balance =this.balance - event.getAmount();
    }



    @EventSourcingHandler
    public void on(AccountStatusUpdatedEvent event) {
        log.info("---------------AccountStatusUpdatedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }
}
