package com.enset.eventsourcingcqrs.query.entities;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import lombok.*;

import java.time.Instant;
import java.util.Date;
@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private Instant createdAt;
    private String currency;
}
