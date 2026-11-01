package com.livesports;

import com.livesports.observer.EventType;
import com.livesports.observer.SportEvent;
import com.livesports.observer.source.MatchEventSource;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcurrentEventSimulatorTest {

    @Test
    void shouldProcess4000EventsWithoutLosingAny() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger receivedEvents = new AtomicInteger(0);

        matchSource.subscribe(
                EventType.GOAL,
                (SportEvent event) -> receivedEvents.incrementAndGet()
        );

        ConcurrentEventSimulator simulator =
                new ConcurrentEventSimulator(matchSource, 4);

        simulator.simulateEvents(1000);

        assertEquals(4000, receivedEvents.get());
    }
}

