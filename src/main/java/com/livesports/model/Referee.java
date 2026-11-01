package com.livesports.model;

import com.livesports.visitor.Visitor;
public class Referee implements SportElement {
    private final String name;
    private final double salary;
    private final int matchesOfficiated;

    public Referee(String name, double salary, int matchesOfficiated) {
        this.name = name;
        this.salary = salary;
        this.matchesOfficiated = matchesOfficiated;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getMatchesOfficiated() {
        return matchesOfficiated;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitReferee(this);
    }
}

