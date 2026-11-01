package com.livesports;

import com.livesports.model.*;
import com.livesports.observer.EventType;
import com.livesports.observer.listeners.StatisticsObserver;
import com.livesports.observer.listeners.TeamAnalysisObserver;
import com.livesports.observer.source.MatchEventSource;
import com.livesports.visitor.*;
import java.util.List;

public class LiveSportsDemo {
    public static void runDemo() {

        League league = new League("Champions League");

        Club realMadrid = new Club("Real Madrid");
        Club manCity = new Club("Manchester City");

        Team madridTeam = new Team("First Team");
        Team cityTeam = new Team("First Team");

        Player mbappe = new Player("Mbappe", 1000000, 9.2, 15);
        Player vinicius = new Player("Vinicius", 800000, 8.8, 12);

        Coach madridCoach =
                new Coach("Carlo Ancelotti", 500000, 20);

        Referee referee =
                new Referee("Michael Oliver", 10000, 120);

        MedicalStaff madridDoctor =
                new MedicalStaff("Medical Staff 1", 8000, "Physiotherapist");

        Player haaland = new Player("Haaland", 900000, 9.5, 18);
        Player foden = new Player("Foden", 600000, 8.4, 10);

        Coach cityCoach =
                new Coach("Pep Guardiola", 700000, 15);

        MedicalStaff cityDoctor =
                new MedicalStaff("Medical Staff 2", 7500, "Sports Doctor");

        madridTeam.addElement(mbappe);
        madridTeam.addElement(vinicius);
        madridTeam.addElement(madridCoach);
        madridTeam.addElement(referee);
        madridTeam.addElement(madridDoctor);
        cityTeam.addElement(haaland);
        cityTeam.addElement(foden);
        cityTeam.addElement(cityCoach);
        cityTeam.addElement(cityDoctor);
        realMadrid.addElement(madridTeam);
        manCity.addElement(cityTeam);

        league.addElement(realMadrid);
        league.addElement(manCity);

        System.out.println("\n=== SALARY ANALYSIS ===");

        SalaryVisitor salaryVisitor = new SalaryVisitor();

        double totalSalary = league.accept(salaryVisitor);

        System.out.println("Total salaries: " + totalSalary);
        System.out.println("\n=== PLAYER RATING ANALYSIS ===");

        RatingVisitor ratingVisitor = new RatingVisitor();

        double averageRating = league.accept(ratingVisitor);

        System.out.println("Average player rating: " + averageRating);
        System.out.println("\n=== PLAYERS WITH RATING >= 8.5 ===");

        FilterVisitor filterVisitor = new FilterVisitor(8.5);

        List<Player> filteredPlayers = league.accept(filterVisitor);

        for (Player player : filteredPlayers) {
            System.out.println(
                    player.getName() + " - Rating: " + player.getRating()
            );
        }
        System.out.println("\n=== JSON EXPORT ===");

        JsonExportVisitor jsonVisitor = new JsonExportVisitor();

        String json = league.accept(jsonVisitor);

        System.out.println(json);
        System.out.println("\n=== XML EXPORT ===");

        XmlExportVisitor xmlVisitor = new XmlExportVisitor();

        String xml = league.accept(xmlVisitor);

        System.out.println(xml);
        System.out.println("\n=== OBSERVER + VISITOR INTEGRATION ===");

        MatchEventSource matchSource = new MatchEventSource();

        StatisticsObserver statisticsObserver =
                new StatisticsObserver();

        TeamAnalysisObserver teamAnalysisObserver =
                new TeamAnalysisObserver(matchSource, madridTeam);

        matchSource.subscribe(
                EventType.GOAL,
                teamAnalysisObserver
        );

        matchSource.subscribe(
                EventType.STATISTICS_UPDATED,
                statisticsObserver
        );

        matchSource.reportGoal("MATCH-001", "Mbappe");
        System.out.println("\n=== DEMO FINISHED ===");
    }
}