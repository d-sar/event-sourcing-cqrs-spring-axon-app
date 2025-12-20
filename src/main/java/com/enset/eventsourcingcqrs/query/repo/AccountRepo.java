package com.enset.eventsourcingcqrs.query.repo;

import com.enset.eventsourcingcqrs.query.entities.Account;
import com.enset.eventsourcingcqrs.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, String> {

}
