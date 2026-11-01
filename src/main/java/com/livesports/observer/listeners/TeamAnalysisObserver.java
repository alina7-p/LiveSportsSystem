package com.livesports.observer.listeners;

import com.livesports.model.Player;
import com.livesports.model.Team;
import com.livesports.observer.EventType;
import com.livesports.observer.Observer;
import com.livesports.observer.SportEvent;
import com.livesports.observer.Subject;
import com.livesports.visitor.FilterVisitor;
import com.livesports.visitor.RatingVisitor;
import com.livesports.visitor.SalaryVisitor;
import java.time.Instant;
import java.util.List;

public class TeamAnalysisObserver implements Observer<SportEvent> {
    private final Subject<SportEvent> eventPublisher;
    private final Team team;

    public TeamAnalysisObserver(
            Subject<SportEvent> eventPublisher,
            Team team
    ) {
        this.eventPublisher = eventPublisher;
        this.team = team;
    }
    @Override
    public void onEvent(SportEvent event) {

        if (event.getType() != EventType.GOAL) {
            return;
        }

        double totalSalary = team.accept(new SalaryVisitor());
        double averageRating = team.accept(new RatingVisitor());

        List<Player> topPlayers =
                team.accept(new FilterVisitor(8.5));

        StringBuilder report = new StringBuilder();

        report.append("Team analysis for ")
                .append(team.getName())
                .append(". ");

        report.append("Total salary: ")
                .append(totalSalary)
                .append(". ");

        report.append("Average player rating: ")
                .append(averageRating)
                .append(". ");

        report.append("Players with rating >= 8.5: ");

        for (Player player : topPlayers) {
            report.append(player.getName())
                    .append(" (")
                    .append(player.getRating())
                    .append("), ");
        }
        SportEvent analysisEvent = new SportEvent(
                EventType.STATISTICS_UPDATED,
                event.getMatchId(),
                report.toString(),
                Instant.now()
        );
        eventPublisher.publish(analysisEvent);
    }
}
