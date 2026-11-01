package com.livesports.observer.listeners;

import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.Subject;

public class LateJoinObserver implements Observer<SportEvent> {
    private final Subject<SportEvent> subject;
    private final Observer<SportEvent> newObserver;

    public LateJoinObserver(
            Subject<SportEvent> subject,
            Observer<SportEvent> newObserver
    ) {
        this.subject = subject;
        this.newObserver = newObserver;
    }

    @Override
    public void onEvent(SportEvent event) {
        System.out.println(
                "[LATE JOIN] A new observer is subscribing!"
        );
        subject.subscribe(EventType.MATCH_FINISHED, newObserver);
    }
}

