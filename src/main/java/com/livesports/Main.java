package com.livesports;

import com.livesports.observer.EventType;
import com.livesports.observer.source.MatchEventSource;
import com.livesports.observer.source.PlayerMetricSource;
import com.livesports.observer.listeners.*;

public class Main {
    public static void main(String[] args) {

        MatchEventSource matchSource = new MatchEventSource();
        PlayerMetricSource metricSource = new PlayerMetricSource();

        ScoreboardObserver scoreboard = new ScoreboardObserver();
        OddsCalculatorObserver odds = new OddsCalculatorObserver();
        FantasyPointsObserver fantasy = new FantasyPointsObserver();
        PushNotifierObserver notifier = new PushNotifierObserver();

        StatisticsAggregator aggregator = new StatisticsAggregator();
        StatisticsObserver statistics = new StatisticsObserver();

        SelfUnsubscribingObserver temporaryObserver =
                new SelfUnsubscribingObserver(matchSource);

        LateJoinNotificationObserver lateNotification =
                new LateJoinNotificationObserver();

        LateJoinObserver lateJoiner =
                new LateJoinObserver(matchSource, lateNotification);

        matchSource.subscribe(EventType.GOAL, scoreboard);
        matchSource.subscribe(EventType.GOAL, odds);
        matchSource.subscribe(EventType.GOAL, fantasy);
        matchSource.subscribe(EventType.GOAL, notifier);
        matchSource.subscribe(EventType.GOAL, aggregator);
        matchSource.subscribe(EventType.GOAL, temporaryObserver);
        matchSource.subscribe(EventType.GOAL, lateJoiner);

        matchSource.subscribe(EventType.RED_CARD, odds);
        matchSource.subscribe(EventType.RED_CARD, notifier);
        matchSource.subscribe(EventType.YELLOW_CARD, notifier);
        matchSource.subscribe(EventType.MATCH_FINISHED, notifier);

        aggregator.subscribe(
                EventType.STATISTICS_UPDATED,
                statistics
        );

        metricSource.subscribe(
                EventType.PLAYER_METRIC_UPDATED,
                fantasy
        );

        System.out.println("=== LIVE SPORTS SYSTEM ===");

        System.out.println("\n--- First goal ---");
        matchSource.reportGoal("MATCH-001", "Mbappe");

        System.out.println("\n--- Second goal ---");
        matchSource.reportGoal("MATCH-001", "Vinicius");

        System.out.println("\n--- Yellow card ---");
        matchSource.reportYellowCard("MATCH-001", "Bellingham");

        System.out.println("\n--- Player rating update ---");
        metricSource.updatePlayerRating(
                "MATCH-001",
                "Mbappe",
                9.2
        );

        System.out.println("\n--- Match finished ---");
        matchSource.finishMatch("MATCH-001");
        System.out.println("\n\n=== VISITOR PATTERN DEMO ===");
        LiveSportsDemo.runDemo();
        System.out.println("\n\n=== CONCURRENCY DEMO ===");

        ConcurrentEventSimulator simulator =
                new ConcurrentEventSimulator(matchSource, 3);

        simulator.simulateEvents(5);

        System.out.println("\n=== PROGRAM FINISHED ===");
    }
}

