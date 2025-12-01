package com.enset.eventsourcingcqrs.commands.Aggregates;

import com.enset.eventsourcingcqrs.commands.commands.AddAccountCommand;
import com.enset.eventsourcingcqrs.commands.events.AccountCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {
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
    }

    // mettre ajour l etat de l aplication
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("---------------AccountCreatedEvent Occurred -----------------");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
