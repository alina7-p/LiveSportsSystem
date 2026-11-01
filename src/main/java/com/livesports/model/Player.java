package com.livesports.model;

import com.livesports.visitor.Visitor;
public class Player implements SportElement {
    private final String name;
    private final double salary;
    private final double rating;
    private final int goals;

    public Player(String name, double salary, double rating, int goals) {
        this.name = name;
        this.salary = salary;
        this.rating = rating;
        this.goals = goals;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public double getRating() {
        return rating;
    }

    public int getGoals() {
        return goals;
    }
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitPlayer(this);
    }
}
