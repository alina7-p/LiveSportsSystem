package com.livesports.observer.source;

import com.livesports.observer.EventType;
import com.livesports.observer.SportEvent;
import java.time.Instant;

public class PlayerMetricSource extends AbstractSubject<SportEvent> {
    public void updatePlayerRating(
            String matchId,
            String playerName,
            double rating
    ) {

        SportEvent event = new SportEvent(
                EventType.PLAYER_METRIC_UPDATED,
                matchId,
                playerName + " rating updated to " + rating,
                Instant.now()
        );
        publish(event);
    }
}
