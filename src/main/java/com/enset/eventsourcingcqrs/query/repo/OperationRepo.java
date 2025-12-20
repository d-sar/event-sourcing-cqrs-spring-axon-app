package com.enset.eventsourcingcqrs.query.repo;

import com.enset.eventsourcingcqrs.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepo extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String id);
}
