package com.regulus.cqrs.core.handlers;

import com.regulus.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregateRoot);

    T getById(String id);
}
