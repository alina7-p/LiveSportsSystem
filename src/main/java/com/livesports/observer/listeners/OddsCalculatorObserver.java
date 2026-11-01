package com.livesports.observer.listeners;

import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.EventType;

public class OddsCalculatorObserver implements Observer<SportEvent> {
    @Override
    public void onEvent(SportEvent event) {

        if (event.getType() == EventType.GOAL ||
                event.getType() == EventType.RED_CARD) {
            System.out.println(
                    "[ODDS] Recalculating odds for " + event.getMatchId()
            );
        }
    }
}
