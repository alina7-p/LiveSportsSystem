package com.livesports.model;

import com.livesports.visitor.Visitor;

public class Analyst implements SportElement {
    private final String name;
    private final double salary;
    private final String specialization;

    public Analyst(String name, double salary, String specialization) {
        this.name = name;
        this.salary = salary;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAnalyst(this);
    }
}