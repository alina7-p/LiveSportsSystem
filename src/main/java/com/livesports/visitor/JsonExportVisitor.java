package com.livesports.visitor;

import com.livesports.model.*;
public class JsonExportVisitor implements Visitor<String> {
    @Override
    public String visitLeague(League league) {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"league\":\"")
                .append(escape(league.getName()))
                .append("\",");

        json.append("\"elements\":[");

        for (int i = 0; i < league.getElements().size(); i++) {
            json.append(league.getElements().get(i).accept(this));

            if (i < league.getElements().size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        return json.toString();
    }
    @Override
    public String visitClub(Club club) {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"club\":\"")
                .append(escape(club.getName()))
                .append("\",");

        json.append("\"elements\":[");

        for (int i = 0; i < club.getElements().size(); i++) {
            json.append(club.getElements().get(i).accept(this));

            if (i < club.getElements().size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        return json.toString();
    }
    @Override
    public String visitTeam(Team team) {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"team\":\"")
                .append(escape(team.getName()))
                .append("\",");

        json.append("\"elements\":[");

        for (int i = 0; i < team.getElements().size(); i++) {
            json.append(team.getElements().get(i).accept(this));

            if (i < team.getElements().size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        return json.toString();
    }
    @Override
    public String visitPlayer(Player player) {
        return "{"
                + "\"type\":\"player\","
                + "\"name\":\"" + escape(player.getName()) + "\","
                + "\"salary\":" + player.getSalary() + ","
                + "\"rating\":" + player.getRating() + ","
                + "\"goals\":" + player.getGoals()
                + "}";
    }
    @Override
    public String visitCoach(Coach coach) {
        return "{"
                + "\"type\":\"coach\","
                + "\"name\":\"" + escape(coach.getName()) + "\","
                + "\"salary\":" + coach.getSalary() + ","
                + "\"experience\":" + coach.getExperience()
                + "}";
    }
    @Override
    public String visitReferee(Referee referee) {
        return "{"
                + "\"type\":\"referee\","
                + "\"name\":\"" + escape(referee.getName()) + "\","
                + "\"salary\":" + referee.getSalary() + ","
                + "\"matchesOfficiated\":" + referee.getMatchesOfficiated()
                + "}";
    }
    @Override
    public String visitMedicalStaff(MedicalStaff medicalStaff) {
        return "{"
                + "\"type\":\"medicalStaff\","
                + "\"name\":\"" + escape(medicalStaff.getName()) + "\","
                + "\"salary\":" + medicalStaff.getSalary() + ","
                + "\"specialization\":\""
                + escape(medicalStaff.getSpecialization()) + "\""
                + "}";
    }

    private String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
