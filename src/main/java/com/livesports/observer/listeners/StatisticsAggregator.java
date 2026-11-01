package com.livesports.observer.listeners;

import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.source.AbstractSubject;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
public class StatisticsAggregator
        extends AbstractSubject<SportEvent>
        implements Observer<SportEvent> {
    private final AtomicInteger totalGoals = new AtomicInteger(0);

    @Override
    public void onEvent(SportEvent event) {
        if (event.getType() == EventType.GOAL) {
            int goals = totalGoals.incrementAndGet();
            SportEvent aggregatedEvent = new SportEvent(
                    EventType.STATISTICS_UPDATED,
                    event.getMatchId(),
                    "Total goals recorded: " + goals,
                    Instant.now()
            );
            publish(aggregatedEvent);
        }
    }
}
