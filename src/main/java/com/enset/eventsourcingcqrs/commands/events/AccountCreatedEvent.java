package com.enset.eventsourcingcqrs.commands.events;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private  double  initialBalance;
    private AccountStatus status;

    private String currency;
}
