package com.example.tournamentTable.Mapper;

import com.example.tournamentTable.DTO.TeamResponse;
import com.example.tournamentTable.Entity.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamResponse toTeamResponse(Team team);

}
