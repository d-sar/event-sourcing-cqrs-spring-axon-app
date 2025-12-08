package com.enset.eventsourcingcqrs.commands.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}
