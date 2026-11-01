package com.livesports.observer.source;

import com.livesports.observer.SportEvent;
public class MatchEventSource extends AbstractSubject<SportEvent> {

    public void reportGoal(String matchId, String playerName) {
        SportEvent event = new SportEvent(
                com.livesports.observer.EventType.GOAL,
                matchId,
                playerName + " scored a goal!",
                java.time.Instant.now()
        );
        publish(event);
    }

    public void reportYellowCard(String matchId, String playerName) {
        SportEvent event = new SportEvent(
                com.livesports.observer.EventType.YELLOW_CARD,
                matchId,
                playerName + " received a yellow card!",
                java.time.Instant.now()
        );
        publish(event);
    }

    public void finishMatch(String matchId) {
        SportEvent event = new SportEvent(
                com.livesports.observer.EventType.MATCH_FINISHED,
                matchId,
                "The match has finished.",
                java.time.Instant.now()
        );
        publish(event);
    }
}

