package com.enset.eventsourcingcqrs.commands.events;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;

}
