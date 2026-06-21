package com.example.tournamentTable.Mapper;

import com.example.tournamentTable.DTO.PlayerResponse;
import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    @Mapping(source = "team", target = "team", qualifiedByName = "teamToString")
    PlayerResponse toPlayerResponse(Player player);

    @Named("teamToString")
    default String teamToString(Team team){
        if(team == null){
            return "";
        }
        else{
            return team.getTitle();
        }
    }
}
