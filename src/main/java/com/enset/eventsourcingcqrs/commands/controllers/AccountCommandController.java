package com.enset.eventsourcingcqrs.commands.controllers;

import com.enset.eventsourcingcqrs.commands.commands.AddAccountCommand;
import com.enset.eventsourcingcqrs.commands.dto.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")

public class AccountCommandController {
    private CommandGateway commandGateway;
    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
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
    //pour recuperer les exeption
    @ExceptionHandler(Exception.class)
    public String exeptionHandler(Exception exception){
        return exception.getMessage();
    }
}
