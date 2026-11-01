package com.livesports;

import com.livesports.model.*;
import com.livesports.visitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitorPatternTest {
    private Team createTeam() {
        Team team = new Team("Real Madrid");

        team.addElement(new Player("Mbappe", 1000000, 9.2, 20));
        team.addElement(new Player("Vinicius", 800000, 8.8, 15));
        team.addElement(new Player("Rodrygo", 700000, 7.5, 10));

        team.addElement(new Coach("Ancelotti", 500000, 20));
        team.addElement(new Referee("John", 100000, 50));
        team.addElement(new MedicalStaff("David", 50000, "Doctor"));

        return team;
    }

    @Test
    void shouldCalculateTotalSalary() {
        Team team = createTeam();
        SalaryVisitor visitor = new SalaryVisitor();
        double result = team.accept(visitor);
        assertEquals(3150000.0, result, 0.01);
    }

    @Test
    void shouldCalculateAveragePlayerRating() {
        Team team = createTeam();
        RatingVisitor visitor = new RatingVisitor();
        double result = team.accept(visitor);
        assertEquals((9.2 + 8.8 + 7.5) / 3, result, 0.01);
    }

    @Test
    void shouldFilterPlayersByMinimumRating() {
        Team team = createTeam();
        FilterVisitor visitor = new FilterVisitor(8.5);
        var result = team.accept(visitor);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("Mbappe")));
        assertTrue(result.stream().anyMatch(p -> p.getName().equals("Vinicius")));
    }

    @Test
    void shouldExportTeamToJson() {
        Team team = createTeam();
        JsonExportVisitor visitor = new JsonExportVisitor();
        String result = team.accept(visitor);
        assertNotNull(result);
        assertTrue(result.contains("Real Madrid"));
        assertTrue(result.contains("Mbappe"));
        assertTrue(result.contains("Vinicius"));
    }
}

