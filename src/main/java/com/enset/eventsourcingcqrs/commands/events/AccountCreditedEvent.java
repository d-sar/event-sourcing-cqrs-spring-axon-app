package com.enset.eventsourcingcqrs.commands.events;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class AccountCreditedEvent {
    private String accountId;
    private double amount;
    private String currency;

}
