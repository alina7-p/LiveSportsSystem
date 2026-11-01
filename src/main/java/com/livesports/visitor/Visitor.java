package com.livesports.visitor;

import com.livesports.model.*;
import com.livesports.model.Analyst;

public interface Visitor<R> {
    R visitLeague(League league);
    R visitClub(Club club);
    R visitTeam(Team team);
    R visitPlayer(Player player);
    R visitCoach(Coach coach);
    R visitReferee(Referee referee);
    R visitMedicalStaff(MedicalStaff medicalStaff);
    R visitAnalyst(Analyst analyst);
}
