package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.GamesNotFoundException;
import com.example.tournamentTable.Exception.TeamHaveGamesException;
import com.example.tournamentTable.Repository.GameRepository;
import com.example.tournamentTable.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameTeamService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    @Transactional
    public void addGame(String title1, String title2, int score1, int score2){
        Team team1 = teamService.getTeam(title1);
        Team team2 = teamService.getTeam(title2);
        Game game = new Game(team1, team2, score1, score2);
        gameRepository.save(game);
        if(score1 == score2){
            team1.setTeamScore(team1.getTeamScore() + 1);
            team2.setTeamScore(team2.getTeamScore() + 1);
        }
        else if(score1 > score2){
            team1.setTeamScore(team1.getTeamScore() + 3);
        }
        else{
            team2.setTeamScore(team2.getTeamScore() + 3);
        }
    }

    @Transactional
    public List<Game> getGamesForTeam(String title){
        Team team = teamService.getTeam(title);
        UUID teamId = team.getId();
        List<Game> games = gameRepository.findAllGamesForTeam(teamId);
        if (games.isEmpty()) {
            throw new GamesNotFoundException("Games are not found");
        }
        return games;
    }

    @Transactional(readOnly = true)
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    @Transactional
    public void deleteTeam(String title){
        Team team = teamService.getTeam(title);
        if(!gameRepository.existsByTeam(team.getId())) {
            teamRepository.delete(team);
        }
        else{
            throw new TeamHaveGamesException("Team have games");
        }
    }
}
