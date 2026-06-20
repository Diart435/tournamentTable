package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.PlayerNotInTeamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamPlayerService {
    private final TeamService teamService;
    private final PlayerService playerService;
    @Transactional
    public void addPlayerToTeam(String name, String title){
        Player player = playerService.getPlayer(name);
        Team team = teamService.getTeam(title);
        List<Player> players = team.getPlayers();
        players.add(player);
        player.setTeam(team);
    }

    @Transactional
    public void deletePlayerFromTeam(String name, String title){
        Player player = playerService.getPlayer(name);
        Team team = teamService.getTeam(title);
        List<Player> players = team.getPlayers();

        if(player.getTeam() == null || !player.getTeam().getId().equals(team.getId())){
            throw new PlayerNotInTeamException("Player is not in team");
        }
        players.remove(player);
        player.setTeam(null);
    }

    @Transactional
    public void transferPlayer(String name, String team1, String team2){
        deletePlayerFromTeam(name, team1);
        addPlayerToTeam(name, team2);
    }
}
