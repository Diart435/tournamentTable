package com.example.tournamentTable.Mapper;

import com.example.tournamentTable.DTO.GameResponse;
import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    @Mapping(source = "team1", target = "team1", qualifiedByName = "teamToString")
    @Mapping(source = "team2", target = "team2", qualifiedByName = "teamToString")
    @Mapping(source = "team1", target = "playersTeam1", qualifiedByName = "teamToPlayerNames")
    @Mapping(source = "team2", target = "playersTeam2", qualifiedByName = "teamToPlayerNames")
    GameResponse toGameResponse(Game game);

    @Named("teamToString")
    default String teamToString(Team team){
        return team.getTitle();
    }
    @Named("teamToPlayerNames")
    default List<String> teamToPlayerNames(Team team){
        List<Player> players = team.getPlayers();
        return players.stream().map(Player::getName).toList();
    }
}
