package com.livesports.model;

import com.livesports.visitor.Visitor;
public class Coach implements SportElement {
    private final String name;
    private final double salary;
    private final int experience;

    public Coach(String name, double salary, int experience) {
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCoach(this);
    }
}
