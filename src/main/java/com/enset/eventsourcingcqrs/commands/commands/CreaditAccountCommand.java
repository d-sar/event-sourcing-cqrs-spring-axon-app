package com.enset.eventsourcingcqrs.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor

public class CreaditAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String  currency ;

}
