package com.regulus.cqrs.core.infrastructure;

import com.regulus.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvent(String aggregateId);

}
