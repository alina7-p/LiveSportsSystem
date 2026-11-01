package com.livesports.observer.listeners;

import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.EventType;

public class ScoreboardObserver implements Observer<SportEvent> {
    @Override
    public void onEvent(SportEvent event) {
        if (event.getType() == EventType.GOAL) {
            System.out.println(
                    "[SCOREBOARD] " + event.getDescription()
            );
        }
    }
}
