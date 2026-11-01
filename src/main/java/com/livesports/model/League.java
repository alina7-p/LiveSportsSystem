package com.livesports.model;

import com.livesports.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class League implements SportElement {
    private final String name;
    private final List<SportElement> elements = new ArrayList<>();

    public League(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addElement(SportElement element) {
        elements.add(element);
    }

    public List<SportElement> getElements() {
        return elements;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLeague(this);
    }
}
