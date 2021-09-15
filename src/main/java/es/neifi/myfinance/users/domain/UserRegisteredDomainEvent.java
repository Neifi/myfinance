package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.bus.event.AggregateID;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventID;
import es.neifi.myfinance.shared.domain.bus.event.OccuredOn;

import java.io.Serializable;
import java.util.HashMap;

public class UserRegisteredDomainEvent extends DomainEvent<UserRegisteredDomainEvent> {
    private UserID userID;

    public UserRegisteredDomainEvent(UserID id) {
        super(new AggregateID(id.value()));
        this.userID = id;
    }

    @Override
    protected String eventName() {
        return "userRegistered";
    }

    @Override
    public HashMap<String, Serializable> toPrimitives() {
        return new HashMap<String, Serializable>() {{
            put("userId", UserRegisteredDomainEvent.super.aggregateId().value());

        }};
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


}
