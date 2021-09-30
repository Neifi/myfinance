package es.neifi.myfinance.shared.domain;

import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {
    private List<DomainEvent<?>> domainEvents = new ArrayList<>();

    public final List<DomainEvent<?>> pullEvents() {
        List<DomainEvent<?>> events = domainEvents;

        domainEvents = Collections.emptyList();

        return events;
    }

    protected final void capture(DomainEvent<?> event) {
        domainEvents.add(event);
    }
}
