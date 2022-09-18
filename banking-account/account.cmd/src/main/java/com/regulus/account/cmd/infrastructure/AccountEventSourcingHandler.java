package com.regulus.account.cmd.infrastructure;

import com.regulus.account.cmd.domain.AccountAggregate;
import com.regulus.cqrs.core.domain.AggregateRoot;
import com.regulus.cqrs.core.handlers.EventSourcingHandler;
import com.regulus.cqrs.core.infrastructure.EventStore;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvent(aggregateRoot.getId(), aggregateRoot.getUnCommitChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvent(id);

        if(events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var lastestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(lastestVersion.get());
        }
        return aggregate;
    }
}
