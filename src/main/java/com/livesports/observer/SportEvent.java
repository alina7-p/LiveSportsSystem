package com.livesports.observer;
import java.time.Instant;
public final class SportEvent implements Event {
    private final EventType type;
    private final String matchId;
    private final String description;
    private final Instant timestamp;
    public SportEvent(EventType type, String matchId,
                      String description, Instant timestamp) {
        this.type = type;
        this.matchId = matchId;
        this.description = description;
        this.timestamp = timestamp;
    }
    @Override
    public EventType getType() {
        return type;
    }
    public String getMatchId() {
        return matchId;
    }
    public String getDescription() {
        return description;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
}

