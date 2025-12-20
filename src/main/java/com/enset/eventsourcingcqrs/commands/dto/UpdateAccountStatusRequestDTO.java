package com.enset.eventsourcingcqrs.commands.dto;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;

import java.io.Serializable;

public record UpdateAccountStatusRequestDTO (String accountId, AccountStatus status) {
}
