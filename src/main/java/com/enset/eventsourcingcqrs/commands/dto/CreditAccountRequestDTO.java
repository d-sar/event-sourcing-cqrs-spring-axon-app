package com.enset.eventsourcingcqrs.commands.dto;

public record CreditAccountRequestDTO (String accountId, double amount, String currency){
}
