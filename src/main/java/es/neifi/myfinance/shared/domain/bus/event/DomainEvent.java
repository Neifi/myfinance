package es.neifi.myfinance.shared.domain.bus.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public abstract class DomainEvent<T extends DomainEvent<?>>{

    private EventID eventId;
    private AggregateID aggregateId;
    private OccuredOn occurredOn;

    public DomainEvent(AggregateID aggregateId) {
        this.eventId     = new EventID(UUID.randomUUID().toString());
        this.aggregateId = aggregateId;
        this.occurredOn  = new OccuredOn(Timestamp.from(Instant.now()).getTime());
    }

    public DomainEvent(AggregateID aggregateId, EventID eventId, OccuredOn occurredOn) {
        this.eventId = eventId;
        this.aggregateId = aggregateId;
        this.occurredOn = occurredOn;
    }

    protected abstract String eventName();

    protected abstract HashMap<String, Serializable> toPrimitives();

    protected abstract T fromPrimitives(
            AggregateID aggregateId,
            HashMap<String, Serializable> body,
            EventID eventId,
            OccuredOn occurredOn
    );

    public AggregateID aggregateId() {
        return aggregateId;
    }

    public EventID eventId() {
        return eventId;
    }

    public OccuredOn occurredOn() {
        return occurredOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainEvent)) return false;
        DomainEvent<?> that = (DomainEvent<?>) o;
        return eventId.value().equalsIgnoreCase(that.eventId.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId.value());
    }
}
