package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.GameWithCloneException;
import com.example.tournamentTable.Exception.GamesNotFoundException;
import com.example.tournamentTable.Exception.TeamHaveGamesException;
import com.example.tournamentTable.Repository.GameRepository;
import com.example.tournamentTable.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameTeamService {
    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Transactional
    public void addGame(String title1, String title2, int score1, int score2, String season, String date){
        if(!title1.equals(title2)) {
            Team team1 = teamService.getTeam(title1);
            Team team2 = teamService.getTeam(title2);
            LocalDate matchDate;
            try {
                matchDate = LocalDate.parse(date, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new DateTimeParseException("Invalid date format. Please use dd-MM-yyyy", date, 0);
            }
            Game game = new Game(team1, team2, score1, score2, season, matchDate);
            gameRepository.save(game);
            if (score1 == score2) {
                team1.setTeamScore(team1.getTeamScore() + 1);
                team2.setTeamScore(team2.getTeamScore() + 1);
                team1.setDraws(team1.getDraws() + 1);
                team2.setDraws(team2.getDraws() + 1);
            } else if (score1 > score2) {
                team1.setTeamScore(team1.getTeamScore() + 3);
                team1.setWins(team1.getWins() + 1);
                team2.setLosses(team2.getLosses() + 1);
            } else {
                team2.setTeamScore(team2.getTeamScore() + 3);
                team2.setWins(team2.getWins() + 1);
                team1.setLosses(team1.getLosses() + 1);
            }
            team1.setMatches(team1.getMatches() + 1);
            team2.setMatches(team2.getMatches() + 1);
        }
        else {
            throw new GameWithCloneException("Team have a game with a clone");
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

    @Transactional(readOnly = true)
    public List<Game> getGamesDate(String date){
        LocalDate matchDate;
        try {
            matchDate = LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format. Please use dd-MM-yyyy", date, 0);
        }
        List<Game> games = gameRepository.findAllOnDate(matchDate);
        if(games.isEmpty()){
            throw new GamesNotFoundException("Games not found");
        }
        else{
            return games;
        }
    }
}
