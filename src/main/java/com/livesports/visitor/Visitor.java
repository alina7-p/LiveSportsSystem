package com.livesports.visitor;

import com.livesports.model.*;

public interface Visitor<R> {
    R visitLeague(League league);
    R visitClub(Club club);
    R visitTeam(Team team);
    R visitPlayer(Player player);
    R visitCoach(Coach coach);
    R visitReferee(Referee referee);
    R visitMedicalStaff(MedicalStaff medicalStaff);
}
