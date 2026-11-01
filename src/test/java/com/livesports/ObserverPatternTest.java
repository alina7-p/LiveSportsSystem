package com.livesports;

import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.source.MatchEventSource;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverPatternTest {
    @Test
    void shouldNotifySubscribedObserver() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger receivedEvents = new AtomicInteger(0);

        Observer<SportEvent> observer =
                event -> receivedEvents.incrementAndGet();

        matchSource.subscribe(EventType.GOAL, observer);

        matchSource.reportGoal("MATCH-001", "Mbappe");

        assertEquals(1, receivedEvents.get());
    }

    @Test
    void shouldNotNotifyUnsubscribedObserver() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger receivedEvents = new AtomicInteger(0);

        Observer<SportEvent> observer =
                event -> receivedEvents.incrementAndGet();

        matchSource.subscribe(EventType.GOAL, observer);
        matchSource.unsubscribe(EventType.GOAL, observer);

        matchSource.reportGoal("MATCH-001", "Mbappe");

        assertEquals(0, receivedEvents.get());
    }

    @Test
    void shouldAllowObserverToUnsubscribeItself() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger receivedEvents = new AtomicInteger(0);

        class SelfRemovingObserver implements Observer<SportEvent> {

            @Override
            public void onEvent(SportEvent event) {

                receivedEvents.incrementAndGet();

                matchSource.unsubscribe(EventType.GOAL, this);
            }
        }

        SelfRemovingObserver observer = new SelfRemovingObserver();

        matchSource.subscribe(EventType.GOAL, observer);

        matchSource.reportGoal("MATCH-001", "Mbappe");
        matchSource.reportGoal("MATCH-001", "Vinicius");

        assertEquals(1, receivedEvents.get());
    }

    @Test
    void shouldAllowObserverToSubscribeAnotherObserverDuringEvent() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger lateObserverCount = new AtomicInteger(0);

        Observer<SportEvent> lateObserver =
                event -> lateObserverCount.incrementAndGet();

        Observer<SportEvent> firstObserver = event ->
                matchSource.subscribe(EventType.GOAL, lateObserver);

        matchSource.subscribe(EventType.GOAL, firstObserver);

        matchSource.reportGoal("MATCH-001", "Mbappe");
        matchSource.reportGoal("MATCH-001", "Vinicius");

        assertEquals(1, lateObserverCount.get());
    }

    @Test
    void shouldNotifyObserverOnlyAboutSubscribedEventType() {

        MatchEventSource matchSource = new MatchEventSource();

        AtomicInteger receivedEvents = new AtomicInteger(0);

        Observer<SportEvent> observer =
                event -> receivedEvents.incrementAndGet();

        matchSource.subscribe(EventType.GOAL, observer);

        matchSource.reportYellowCard("MATCH-001", "Bellingham");
        matchSource.reportGoal("MATCH-001", "Mbappe");

        assertEquals(1, receivedEvents.get());
    }
}
