package com.livesports.observer.listeners;

import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.Subject;

public class SelfUnsubscribingObserver
        implements Observer<SportEvent> {
    private final Subject<SportEvent> subject;
    public SelfUnsubscribingObserver(Subject<SportEvent> subject) {
        this.subject = subject;
    }

    @Override
    public void onEvent(SportEvent event) {
        System.out.println(
                "[TEMPORARY OBSERVER] Received: "
                        + event.getDescription()
        );
        subject.unsubscribe(event.getType(), this);

        System.out.println(
                "[TEMPORARY OBSERVER] Unsubscribed successfully!"
        );
    }
}
