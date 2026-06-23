package com.example.tournamentTable.Mapper;

import com.example.tournamentTable.DTO.TeamResponse;
import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    @Mapping(source = "players", target = "players", qualifiedByName = "playersToString")
    TeamResponse toTeamResponse(Team team);

    @Named("playersToString")
    default String[] playersToString(List<Player> players){
        return players.stream().map(Player::getName).toArray(String[]::new);
    }
}
