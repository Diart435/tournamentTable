package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.TeamNotFoundException;
import com.example.tournamentTable.Repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    public TeamService(TeamRepository teamRepository, PlayerService playerService){
        this.teamRepository = teamRepository;
        this.playerService = playerService;
    }

    public Team getTeam(String title){
        Optional<Team> existing = teamRepository.findByTitle(title);
        if(existing.isPresent()) {
            return existing.get();
        }
        else{
            throw new TeamNotFoundException("Team is not found");
        }
    }

    public void addPlayerToTeam(String title, String name){
        Player player = playerService.getPlayer(name);
        Team team = getTeam(title);
        List<Player> players = team.getPlayers();
        players.add(player);
        team.setPlayers(players);
    }


}
