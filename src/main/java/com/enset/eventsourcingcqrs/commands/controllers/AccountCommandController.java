package com.enset.eventsourcingcqrs.commands.controllers;

import com.enset.eventsourcingcqrs.commands.Aggregates.AccountAggregate;
import com.enset.eventsourcingcqrs.commands.commands.AddAccountCommand;
import com.enset.eventsourcingcqrs.commands.commands.CreaditAccountCommand;
import com.enset.eventsourcingcqrs.commands.commands.DebitAccountCommand;
import com.enset.eventsourcingcqrs.commands.dto.AddNewAccountRequestDTO;
import com.enset.eventsourcingcqrs.commands.dto.CreditAccountRequestDTO;
import com.enset.eventsourcingcqrs.commands.dto.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")

public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    private AccountAggregate accountAggregate;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore, AccountAggregate accountAggregate) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
        this.accountAggregate = accountAggregate;
    }
    @PostMapping("/add")
    public CompletableFuture<String>  addNewAccount( @RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> reponse = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));

        return reponse;

    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new CreaditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    //pour recuperer les exeption
    @ExceptionHandler(Exception.class)
    public String exeptionHandler(Exception exception){
        return exception.getMessage();
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
