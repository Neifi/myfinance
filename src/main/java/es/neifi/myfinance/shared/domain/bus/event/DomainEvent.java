package es.neifi.myfinance.shared.domain.bus.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@EqualsAndHashCode
@Getter
public abstract class DomainEvent<T extends DomainEvent<?>> {

    private String aggregateId;
    private String eventId;
    private Long occurredOn;

    public DomainEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.eventId     = UUID.randomUUID().toString();
        this.occurredOn  = Timestamp.from(Instant.now()).getTime();
    }

    public DomainEvent(String aggregateId, String eventId, Long occurredOn) {
        this.aggregateId = aggregateId;
        this.eventId = eventId;
        this.occurredOn = occurredOn;
    }

    protected abstract String eventName();

    protected abstract HashMap<String, Serializable> toPrimitives();

    protected abstract T fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            Long occurredOn
    );
}
