package com.livesports;

import com.livesports.observer.source.MatchEventSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentEventSimulator {
    private final MatchEventSource matchEventSource;
    private final int numberOfThreads;

    public ConcurrentEventSimulator(
            MatchEventSource matchEventSource,
            int numberOfThreads
    ) {
        this.matchEventSource = matchEventSource;
        this.numberOfThreads = numberOfThreads;
    }

    public void simulateEvents(int eventsPerThread) {

        ExecutorService executor =
                Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {

            int producerId = i + 1;

            executor.submit(() -> {

                for (int j = 1; j <= eventsPerThread; j++) {

                    String matchId = "MATCH-" + producerId;

                    String playerName =
                            "Player-" + producerId + "-" + j;

                    matchEventSource.reportGoal(
                            matchId,
                            playerName
                    );
                }
            });
        }

        executor.shutdown();

        try {
            boolean finished = executor.awaitTermination(
                    1,
                    TimeUnit.MINUTES
            );

            if (!finished) {
                executor.shutdownNow();
                System.out.println("Some tasks did not finish in time.");
            }

        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("Event simulation was interrupted.");
        }
    }
}
