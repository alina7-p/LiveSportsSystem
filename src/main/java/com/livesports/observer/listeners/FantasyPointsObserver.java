package com.livesports.observer.listeners;

import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.EventType;

public class FantasyPointsObserver implements Observer<SportEvent> {
    @Override
    public void onEvent(SportEvent event) {

        if (event.getType() == EventType.GOAL) {
            System.out.println(
                    "[FANTASY] Goal points awarded: "
                            + event.getDescription()
            );

        } else if (event.getType() == EventType.PLAYER_METRIC_UPDATED) {
            System.out.println(
                    "[FANTASY] Player metrics updated: "
                            + event.getDescription()
            );
        }
    }
}
