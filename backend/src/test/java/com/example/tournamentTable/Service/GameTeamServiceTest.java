package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.GameWithCloneException;
import com.example.tournamentTable.Exception.GamesNotFoundException;
import com.example.tournamentTable.Exception.TeamHaveGamesException;
import com.example.tournamentTable.Exception.TeamNotFoundException;
import com.example.tournamentTable.Repository.GameRepository;
import com.example.tournamentTable.Repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTeamServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private GameTeamService gameTeamService;

    private Team team1;
    private Team team2;
    private String season;
    private String validDateStr;
    private LocalDate validDate;

    @BeforeEach
    void setUp() {
        season = "2023-2024";
        validDateStr = "15-03-2024";
        validDate = LocalDate.of(2024, 3, 15);

        team1 = new Team("Team A");
        team1.setId(UUID.randomUUID());
        team1.setTeamScore(0);

        team2 = new Team("Team B");
        team2.setId(UUID.randomUUID());
        team2.setTeamScore(0);
    }

    @Test
    void addGame_ShouldCreateGameAndUpdateScores_WhenTeam1Wins() {
        int score1 = 3;
        int score2 = 1;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        assertEquals(3, team1.getTeamScore());
        assertEquals(0, team2.getTeamScore());

        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(1)).save(gameCaptor.capture());

        Game savedGame = gameCaptor.getValue();
        assertEquals(team1, savedGame.getTeam1());
        assertEquals(team2, savedGame.getTeam2());
        assertEquals(score1, savedGame.getScore1());
        assertEquals(score2, savedGame.getScore2());
        assertEquals(season, savedGame.getSeason());
        assertEquals(validDate, savedGame.getMatchDate());
    }

    @Test
    void addGame_ShouldCreateGameAndUpdateScores_WhenTeam2Wins() {
        int score1 = 1;
        int score2 = 3;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        assertEquals(0, team1.getTeamScore());
        assertEquals(3, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldCreateGameAndUpdateScores_WhenDraw() {
        int score1 = 2;
        int score2 = 2;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        assertEquals(1, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldThrowGameWithCloneException_WhenTeamsAreSame() {
        String sameTeam = "Team A";

        assertThrows(GameWithCloneException.class,
                () -> gameTeamService.addGame(sameTeam, sameTeam, 2, 1, season, validDateStr));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldThrowDateTimeParseException_WhenInvalidDateFormat() {
        String invalidDate = "2024-03-15";

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.addGame("Team A", "Team B", 2, 1, season, invalidDate));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldThrowDateTimeParseException_WhenDateIsEmpty() {
        String emptyDate = "";

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.addGame("Team A", "Team B", 2, 1, season, emptyDate));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldThrowNullPointerException_WhenDateIsNull() {
        String nullDate = null;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        assertThrows(NullPointerException.class,
                () -> gameTeamService.addGame("Team A", "Team B", 2, 1, season, nullDate));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldThrowTeamNotFoundException_WhenTeam1NotExists() {
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.addGame("NonExistent", "Team B", 2, 1, season, validDateStr));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldThrowTeamNotFoundException_WhenTeam2NotExists() {
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.addGame("Team A", "NonExistent", 2, 1, season, validDateStr));

        verify(gameRepository, never()).save(any(Game.class));
        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void addGame_ShouldHandleZeroScore() {
        int score1 = 0;
        int score2 = 0;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        assertEquals(1, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldHandleLargeScoreDifference() {
        int score1 = 10;
        int score2 = 0;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        assertEquals(3, team1.getTeamScore());
        assertEquals(0, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldUpdateTeamScoresCorrectly_MultipleGames() {
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        gameTeamService.addGame("Team A", "Team B", 2, 0, season, validDateStr);
        assertEquals(3, team1.getTeamScore());
        assertEquals(0, team2.getTeamScore());

        gameTeamService.addGame("Team A", "Team B", 1, 1, season, validDateStr);
        assertEquals(4, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());

        verify(gameRepository, times(2)).save(any(Game.class));
    }


    @Test
    void getGamesForTeam_ShouldReturnGames_WhenGamesExist() {
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.findAllGamesForTeam(team1.getId())).thenReturn(games);

        List<Game> result = gameTeamService.getGamesForTeam("Team A");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamService, times(1)).getTeam("Team A");
        verify(gameRepository, times(1)).findAllGamesForTeam(team1.getId());
    }

    @Test
    void getGamesForTeam_ShouldThrowGamesNotFoundException_WhenNoGames() {
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.findAllGamesForTeam(team1.getId())).thenReturn(new ArrayList<>());

        assertThrows(GamesNotFoundException.class,
                () -> gameTeamService.getGamesForTeam("Team A"));
    }

    @Test
    void getGamesForTeam_ShouldThrowTeamNotFoundException_WhenTeamNotExists() {
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.getGamesForTeam("NonExistent"));

        verify(gameRepository, never()).findAllGamesForTeam(any(UUID.class));
    }

    @Test
    void getAllGames_ShouldReturnAllGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(gameRepository.findAll()).thenReturn(games);

        List<Game> result = gameTeamService.getAllGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void getAllGames_ShouldReturnEmptyList_WhenNoGames() {
        when(gameRepository.findAll()).thenReturn(new ArrayList<>());

        List<Game> result = gameTeamService.getAllGames();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void deleteTeam_ShouldDeleteTeam_WhenNoGamesExist() {
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.existsByTeam(team1.getId())).thenReturn(false);

        gameTeamService.deleteTeam("Team A");

        verify(teamRepository, times(1)).delete(team1);
        verify(gameRepository, times(1)).existsByTeam(team1.getId());
    }

    @Test
    void deleteTeam_ShouldThrowTeamHaveGamesException_WhenGamesExist() {
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.existsByTeam(team1.getId())).thenReturn(true);

        assertThrows(TeamHaveGamesException.class,
                () -> gameTeamService.deleteTeam("Team A"));

        verify(teamRepository, never()).delete(team1);
    }

    @Test
    void deleteTeam_ShouldThrowTeamNotFoundException_WhenTeamNotExists() {
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.deleteTeam("NonExistent"));

        verify(teamRepository, never()).delete(any(Team.class));
    }

    @Test
    void getGamesDate_ShouldReturnGames_WhenGamesExistOnDate() {
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(gameRepository.findAllOnDate(validDate)).thenReturn(games);

        List<Game> result = gameTeamService.getGamesDate(validDateStr);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAllOnDate(validDate);
    }

    @Test
    void getGamesDate_ShouldThrowGamesNotFoundException_WhenNoGamesOnDate() {
        when(gameRepository.findAllOnDate(validDate)).thenReturn(new ArrayList<>());

        assertThrows(GamesNotFoundException.class,
                () -> gameTeamService.getGamesDate(validDateStr));

        verify(gameRepository, times(1)).findAllOnDate(validDate);
    }

    @Test
    void getGamesDate_ShouldThrowDateTimeParseException_WhenInvalidDateFormat() {
        String invalidDate = "2024-03-15";

        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.getGamesDate(invalidDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldThrowDateTimeParseException_WhenDateIsEmpty() {
        String emptyDate = "";

        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.getGamesDate(emptyDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldThrowNullPointerException_WhenDateIsNull() {
        String nullDate = null;
        assertThrows(NullPointerException.class,
                () -> gameTeamService.getGamesDate(nullDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldReturnGamesSortedDescending() {
        LocalDate date1 = LocalDate.of(2024, 3, 15);
        LocalDate date2 = LocalDate.of(2024, 3, 14);

        Game game1 = new Game(team1, team2, 2, 1, season, date1);
        Game game2 = new Game(team1, team2, 1, 0, season, date2);
        List<Game> games = List.of(game1, game2);

        when(gameRepository.findAllOnDate(any(LocalDate.class))).thenReturn(games);

        List<Game> result = gameTeamService.getGamesDate(validDateStr);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAllOnDate(any(LocalDate.class));
    }
}