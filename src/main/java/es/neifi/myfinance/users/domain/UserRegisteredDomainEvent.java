package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.bus.event.AggregateID;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventID;
import es.neifi.myfinance.shared.domain.bus.event.OccuredOn;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class UserRegisteredDomainEvent extends DomainEvent<UserRegisteredDomainEvent> {
    private final UserID userID;

    public UserRegisteredDomainEvent(UserID userID) {

        super(
                new AggregateID(userID.value()),
                new EventID(UUID.randomUUID().toString()),
                new OccuredOn(Timestamp.from(Instant.now()).getTime())
        );
        this.userID = userID;
    }

    @Override
    protected String eventName() {
        return "userRegistered";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        HashMap<String, Serializable> hashmap = new HashMap<>();
        hashmap.put("userId", UserRegisteredDomainEvent.super.aggregateId().value());
        return hashmap;
    }

    @Override
    protected UserRegisteredDomainEvent fromPrimitives(AggregateID aggregateId, HashMap<String, Serializable> body, EventID eventId, OccuredOn occurredOn) {
        return new UserRegisteredDomainEvent(
                new UserID((String) body.get("userId"))
        );
    }

    public UserID userID() {
        return userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainEvent)) return false;
        DomainEvent<?> that = (DomainEvent<?>) o;
        return super.eventId().value().equalsIgnoreCase(that.eventId().value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId().value());
    }

}
