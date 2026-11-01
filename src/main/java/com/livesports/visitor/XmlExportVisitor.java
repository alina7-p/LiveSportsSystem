package com.livesports.visitor;

import com.livesports.model.*;

public class XmlExportVisitor implements Visitor<String> {
    @Override
    public String visitLeague(League league) {
        StringBuilder xml = new StringBuilder();

        xml.append("<league name=\"")
                .append(escape(league.getName()))
                .append("\">");

        for (SportElement element : league.getElements()) {
            xml.append(element.accept(this));
        }

        xml.append("</league>");

        return xml.toString();
    }
    @Override
    public String visitClub(Club club) {
        StringBuilder xml = new StringBuilder();

        xml.append("<club name=\"")
                .append(escape(club.getName()))
                .append("\">");

        for (SportElement element : club.getElements()) {
            xml.append(element.accept(this));
        }

        xml.append("</club>");

        return xml.toString();
    }
    @Override
    public String visitTeam(Team team) {
        StringBuilder xml = new StringBuilder();

        xml.append("<team name=\"")
                .append(escape(team.getName()))
                .append("\">");

        for (SportElement element : team.getElements()) {
            xml.append(element.accept(this));
        }

        xml.append("</team>");

        return xml.toString();
    }
    @Override
    public String visitPlayer(Player player) {
        return "<player>"
                + "<name>" + escape(player.getName()) + "</name>"
                + "<salary>" + player.getSalary() + "</salary>"
                + "<rating>" + player.getRating() + "</rating>"
                + "<goals>" + player.getGoals() + "</goals>"
                + "</player>";
    }
    @Override
    public String visitCoach(Coach coach) {
        return "<coach>"
                + "<name>" + escape(coach.getName()) + "</name>"
                + "<salary>" + coach.getSalary() + "</salary>"
                + "<experience>" + coach.getExperience() + "</experience>"
                + "</coach>";
    }
    @Override
    public String visitReferee(Referee referee) {
        return "<referee>"
                + "<name>" + escape(referee.getName()) + "</name>"
                + "<salary>" + referee.getSalary() + "</salary>"
                + "<matchesOfficiated>" + referee.getMatchesOfficiated()
                + "</matchesOfficiated>"
                + "</referee>";
    }
    @Override
    public String visitMedicalStaff(MedicalStaff medicalStaff) {
        return "<medicalStaff>"
                + "<name>" + escape(medicalStaff.getName()) + "</name>"
                + "<salary>" + medicalStaff.getSalary() + "</salary>"
                + "<specialization>"
                + escape(medicalStaff.getSpecialization())
                + "</specialization>"
                + "</medicalStaff>";
    }
    @Override
    public String visitAnalyst(Analyst analyst) {
        return "<analyst>"
                + "<name>" + escape(analyst.getName()) + "</name>"
                + "<salary>" + analyst.getSalary() + "</salary>"
                + "<specialization>" + escape(analyst.getSpecialization()) + "</specialization>"
                + "</analyst>";
    }

    private String escape(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
