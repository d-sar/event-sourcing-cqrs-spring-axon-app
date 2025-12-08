package com.enset.eventsourcingcqrs.commands.events;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class AccountActivatedEvent {
    private String accountId;
    private AccountStatus status;


}
