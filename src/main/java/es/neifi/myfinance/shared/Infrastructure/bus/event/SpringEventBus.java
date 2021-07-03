package es.neifi.myfinance.shared.Infrastructure.bus.event;

import es.neifi.myfinance.shared.domain.Service;
import es.neifi.myfinance.shared.domain.bus.event.DomainEvent;
import es.neifi.myfinance.shared.domain.bus.event.EventBus;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Service
public class SpringEventBus implements EventBus {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventBus(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(List<DomainEvent<?>> events) {
        events.forEach(this::publish);
    }

    public void publish(final DomainEvent event) {
        this.applicationEventPublisher.publishEvent(event);
    }

}
