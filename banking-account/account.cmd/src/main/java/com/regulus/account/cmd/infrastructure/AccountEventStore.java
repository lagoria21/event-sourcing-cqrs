package com.regulus.account.cmd.infrastructure;

import com.regulus.account.cmd.domain.EventStoreRepository;
import com.regulus.cqrs.core.events.BaseEvent;
import com.regulus.cqrs.core.events.EventModel;
import com.regulus.cqrs.core.exceptions.AggregateNotFoundException;
import com.regulus.cqrs.core.exceptions.ConcurrencyException;
import com.regulus.cqrs.core.infrastructure.EventStore;
import com.regulus.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvent(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {

     var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() -1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }

        var version = expectedVersion;

        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder().timeState(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountEventStore.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);

            if(!persistedEvent.getId().isEmpty()) {
                 eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty() ){
            throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
        }

        return eventStream.stream().map(eventModel -> eventModel.getEventData()).collect(Collectors.toList());
    }
}
