package com.enset.eventsourcingcqrs.commands.commands;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor

public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
  private AccountStatus status;

}
