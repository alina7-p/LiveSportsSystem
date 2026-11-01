package com.livesports.visitor;

import com.livesports.model.*;

public class GoalsVisitor implements Visitor<Integer> {
    @Override
    public Integer visitLeague(League league) {
        int totalGoals = 0;

        for (SportElement element : league.getElements()) {
            totalGoals += element.accept(this);
        }
        return totalGoals;
    }
    @Override
    public Integer visitClub(Club club) {
        int totalGoals = 0;

        for (SportElement element : club.getElements()) {
            totalGoals += element.accept(this);
        }
        return totalGoals;
    }
    @Override
    public Integer visitTeam(Team team) {
        int totalGoals = 0;

        for (SportElement element : team.getElements()) {
            totalGoals += element.accept(this);
        }
        return totalGoals;
    }
    @Override
    public Integer visitPlayer(Player player) {
        return player.getGoals();
    }
    @Override
    public Integer visitCoach(Coach coach) {
        return 0;
    }
    @Override
    public Integer visitReferee(Referee referee) {
        return 0;
    }
    @Override
    public Integer visitMedicalStaff(MedicalStaff staff) {
        return 0;
    }
    @Override
    public Integer visitAnalyst(Analyst analyst) {
        return 0;
    }
}