package com.enset.eventsourcingcqrs.commands.dto;

public record AddNewAccountRequestDTO (double initialBalance, String currency){
}
