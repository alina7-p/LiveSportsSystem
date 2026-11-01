package com.livesports.visitor;

import com.livesports.model.*;
public class RatingVisitor implements Visitor<Double> {
    private double totalRating = 0;
    private int playerCount = 0;

    @Override
    public Double visitLeague(League league) {
        for (SportElement element : league.getElements()) {
            element.accept(this);
        }

        return getAverageRating();
    }
    @Override
    public Double visitClub(Club club) {
        for (SportElement element : club.getElements()) {
            element.accept(this);
        }

        return getAverageRating();
    }
    @Override
    public Double visitTeam(Team team) {
        for (SportElement element : team.getElements()) {
            element.accept(this);
        }

        return getAverageRating();
    }
    @Override
    public Double visitPlayer(Player player) {
        totalRating += player.getRating();
        playerCount++;

        return getAverageRating();
    }
    @Override
    public Double visitCoach(Coach coach) {
        return getAverageRating();
    }
    @Override
    public Double visitReferee(Referee referee) {
        return getAverageRating();
    }
    @Override
    public Double visitMedicalStaff(MedicalStaff medicalStaff) {
        return getAverageRating();
    }

    private double getAverageRating() {
        if (playerCount == 0) {
            return 0;
        }

        return totalRating / playerCount;
    }
}
