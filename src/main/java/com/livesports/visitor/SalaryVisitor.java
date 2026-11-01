package com.livesports.visitor;

import com.livesports.model.*;

public class SalaryVisitor implements Visitor<Double> {
    @Override
    public Double visitLeague(League league) {
        double total = 0;

        for (SportElement element : league.getElements()) {
            total += element.accept(this);
        }

        return total;
    }
    @Override
    public Double visitClub(Club club) {
        double total = 0;
        for (SportElement element : club.getElements()) {
            total += element.accept(this);
        }

        return total;
    }
    @Override
    public Double visitTeam(Team team) {
        double total = 0;
        for (SportElement element : team.getElements()) {
            total += element.accept(this);
        }

        return total;
    }
    @Override
    public Double visitPlayer(Player player) {
        return player.getSalary();
    }
    @Override
    public Double visitCoach(Coach coach) {
        return coach.getSalary();
    }
    @Override
    public Double visitReferee(Referee referee) {
        return referee.getSalary();
    }
    @Override
    public Double visitMedicalStaff(MedicalStaff medicalStaff) {
        return medicalStaff.getSalary();
    }
    @Override
    public Double visitAnalyst(Analyst analyst) {
        return analyst.getSalary();
    }
}

