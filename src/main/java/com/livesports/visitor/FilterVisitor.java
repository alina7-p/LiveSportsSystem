package com.livesports.visitor;

import com.livesports.model.*;
import java.util.ArrayList;
import java.util.List;

public class FilterVisitor implements Visitor<List<Player>> {
    private final double minimumRating;
    private final List<Player> filteredPlayers = new ArrayList<>();

    public FilterVisitor(double minimumRating) {
        this.minimumRating = minimumRating;
    }
    @Override
    public List<Player> visitLeague(League league) {
        for (SportElement element : league.getElements()) {
            element.accept(this);
        }

        return filteredPlayers;
    }
    @Override
    public List<Player> visitClub(Club club) {
        for (SportElement element : club.getElements()) {
            element.accept(this);
        }

        return filteredPlayers;
    }
    @Override
    public List<Player> visitTeam(Team team) {
        for (SportElement element : team.getElements()) {
            element.accept(this);
        }

        return filteredPlayers;
    }
    @Override
    public List<Player> visitPlayer(Player player) {
        if (player.getRating() >= minimumRating) {
            filteredPlayers.add(player);
        }

        return filteredPlayers;
    }
    @Override
    public List<Player> visitCoach(Coach coach) {
        return filteredPlayers;
    }
    @Override
    public List<Player> visitReferee(Referee referee) {
        return filteredPlayers;
    }
    @Override
    public List<Player> visitMedicalStaff(MedicalStaff medicalStaff) {
        return filteredPlayers;
    }
    @Override
    public List<Player> visitAnalyst(Analyst analyst) {
        return new ArrayList<>();
    }
}