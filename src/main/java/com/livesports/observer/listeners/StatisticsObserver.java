package com.livesports.observer.listeners;

import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;

public class StatisticsObserver implements Observer<SportEvent> {
    @Override
    public void onEvent(SportEvent event) {

        if (event.getType() == EventType.STATISTICS_UPDATED) {
            System.out.println("\n[STATISTICS OBSERVER]");
            System.out.println("Match ID: " + event.getMatchId());
            System.out.println("Statistics report:");
            System.out.println(event.getDescription());
            System.out.println("Report received at: " + event.getTimestamp());
        }
    }
}
