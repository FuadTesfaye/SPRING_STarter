package com.assignment.order.application.usecase;

import com.assignment.order.infrastructure.catalogue.EventCatalogue;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetEventsUseCase {

    private final EventCatalogue catalogue;

    public GetEventsUseCase(EventCatalogue catalogue) {
        this.catalogue = catalogue;
    }

    public List<EventCatalogue.EventInfo> execute(String category) {
        return catalogue.findAll(category);
    }
}
