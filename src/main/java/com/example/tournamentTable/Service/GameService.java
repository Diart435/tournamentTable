package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Repository.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final TeamService teamService;

    public GameService(GameRepository gameRepository, TeamService teamService){
        this.gameRepository = gameRepository;
        this.teamService = teamService;
    }

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
}
