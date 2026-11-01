package com.livesports.observer.listeners;

import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;

public class LateJoinNotificationObserver
        implements Observer<SportEvent> {
    @Override
    public void onEvent(SportEvent event) {
        System.out.println(
                "[LATE NOTIFICATION] " + event.getDescription()
        );
    }
}
