package com.livesports;

import com.livesports.model.*;
import com.livesports.visitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitorEdgeCasesTest {

    @Test
    void shouldExportTeamToXml() {

        Team team = new Team("Real Madrid");
        team.addElement(new Player("Mbappe", 1000000, 9.2, 20));

        XmlExportVisitor visitor = new XmlExportVisitor();

        String result = team.accept(visitor);

        assertNotNull(result);
        assertTrue(result.contains("Real Madrid"));
        assertTrue(result.contains("Mbappe"));
        assertTrue(result.contains("<team"));
        assertTrue(result.contains("</team>"));
    }


    @Test
    void shouldReturnZeroSalaryForEmptyTeam() {

        Team team = new Team("Empty Team");
        SalaryVisitor visitor = new SalaryVisitor();
        double result = team.accept(visitor);

        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shouldReturnZeroRatingForEmptyTeam() {

        Team team = new Team("Empty Team");
        RatingVisitor visitor = new RatingVisitor();
        double result = team.accept(visitor);

        assertEquals(0.0, result, 0.01);
    }

    @Test
    void shouldReturnEmptyListWhenNoPlayersMatchRating() {

        Team team = new Team("Real Madrid");
        team.addElement(new Player("Rodrygo", 700000, 7.5, 10));
        FilterVisitor visitor = new FilterVisitor(9.0);
        var result = team.accept(visitor);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldIncludeAllPlayersWhenMinimumRatingIsLow() {

        Team team = new Team("Real Madrid");
        team.addElement(new Player("Mbappe", 1000000, 9.2, 20));
        team.addElement(new Player("Vinicius", 800000, 8.8, 15));
        FilterVisitor visitor = new FilterVisitor(0.0);
        var result = team.accept(visitor);

        assertEquals(2, result.size());
    }
}