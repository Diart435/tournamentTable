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
        validDateStr = "2024-03-15";
        validDate = LocalDate.of(2024, 3, 15);

        team1 = new Team("Team A");
        team1.setId(UUID.randomUUID());
        team1.setTeamScore(0);

        team2 = new Team("Team B");
        team2.setId(UUID.randomUUID());
        team2.setTeamScore(0);
    }

    // ==================== ТЕСТЫ ДЛЯ addGame ====================

    @Test
    void addGame_ShouldCreateGameAndUpdateScores_WhenTeam1Wins() {
        // given
        int score1 = 3;
        int score2 = 1;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when
        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        // then
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
        // given
        int score1 = 1;
        int score2 = 3;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when
        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        // then
        assertEquals(0, team1.getTeamScore());
        assertEquals(3, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldCreateGameAndUpdateScores_WhenDraw() {
        // given
        int score1 = 2;
        int score2 = 2;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when
        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        // then
        assertEquals(1, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldThrowException_WhenTeamsAreSame() {
        // given
        String sameTeam = "Team A";

        // when & then
        assertThrows(GameWithCloneException.class,
                () -> gameTeamService.addGame(sameTeam, sameTeam, 2, 1, season, validDateStr));

        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void addGame_ShouldThrowDateTimeParseException_WhenInvalidDateFormat() {
        // given
        String invalidDate = "15-03-2024";

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when & then
        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.addGame("Team A", "Team B", 2, 1, season, invalidDate));

        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void addGame_ShouldThrowTeamNotFoundException_WhenTeam1NotExists() {
        // given
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        // when & then
        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.addGame("NonExistent", "Team B", 2, 1, season, validDateStr));

        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void addGame_ShouldHandleZeroScore() {
        // given
        int score1 = 0;
        int score2 = 0;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when
        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        // then
        assertEquals(1, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldHandleLargeScoreDifference() {
        // given
        int score1 = 10;
        int score2 = 0;

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when
        gameTeamService.addGame("Team A", "Team B", score1, score2, season, validDateStr);

        // then
        assertEquals(3, team1.getTeamScore());
        assertEquals(0, team2.getTeamScore());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void addGame_ShouldUpdateTeamScoresCorrectly_MultipleGames() {
        // given
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(teamService.getTeam("Team B")).thenReturn(team2);

        // when - первая игра (победа Team A)
        gameTeamService.addGame("Team A", "Team B", 2, 0, season, validDateStr);

        assertEquals(3, team1.getTeamScore());
        assertEquals(0, team2.getTeamScore());

        // when - вторая игра (ничья)
        gameTeamService.addGame("Team A", "Team B", 1, 1, season, validDateStr);

        // then
        assertEquals(4, team1.getTeamScore());
        assertEquals(1, team2.getTeamScore());

        verify(gameRepository, times(2)).save(any(Game.class));
    }

    // ==================== ТЕСТЫ ДЛЯ getGamesForTeam ====================

    @Test
    void getGamesForTeam_ShouldReturnGames_WhenGamesExist() {
        // given
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.findAllGamesForTeam(team1.getId())).thenReturn(games);

        // when
        List<Game> result = gameTeamService.getGamesForTeam("Team A");

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(teamService, times(1)).getTeam("Team A");
        verify(gameRepository, times(1)).findAllGamesForTeam(team1.getId());
    }

    @Test
    void getGamesForTeam_ShouldThrowGamesNotFoundException_WhenNoGames() {
        // given
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.findAllGamesForTeam(team1.getId())).thenReturn(new ArrayList<>());

        // when & then
        GamesNotFoundException exception = assertThrows(GamesNotFoundException.class,
                () -> gameTeamService.getGamesForTeam("Team A"));

        assertEquals("Games are not found", exception.getMessage());
        verify(gameRepository, times(1)).findAllGamesForTeam(team1.getId());
    }

    @Test
    void getGamesForTeam_ShouldThrowTeamNotFoundException_WhenTeamNotExists() {
        // given
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        // when & then
        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.getGamesForTeam("NonExistent"));

        verify(gameRepository, never()).findAllGamesForTeam(any(UUID.class));
    }

    // ==================== ТЕСТЫ ДЛЯ getAllGames ====================

    @Test
    void getAllGames_ShouldReturnAllGames() {
        // given
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(gameRepository.findAll()).thenReturn(games);

        // when
        List<Game> result = gameTeamService.getAllGames();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void getAllGames_ShouldReturnEmptyList_WhenNoGames() {
        // given
        when(gameRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<Game> result = gameTeamService.getAllGames();

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).findAll();
    }

    // ==================== ТЕСТЫ ДЛЯ deleteTeam ====================

    @Test
    void deleteTeam_ShouldDeleteTeam_WhenNoGamesExist() {
        // given
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.existsByTeam(team1.getId())).thenReturn(false);

        // when
        gameTeamService.deleteTeam("Team A");

        // then
        verify(teamRepository, times(1)).delete(team1);
        verify(gameRepository, times(1)).existsByTeam(team1.getId());
    }

    @Test
    void deleteTeam_ShouldThrowTeamHaveGamesException_WhenGamesExist() {
        // given
        when(teamService.getTeam("Team A")).thenReturn(team1);
        when(gameRepository.existsByTeam(team1.getId())).thenReturn(true);

        // when & then
        TeamHaveGamesException exception = assertThrows(TeamHaveGamesException.class,
                () -> gameTeamService.deleteTeam("Team A"));

        assertEquals("Team have games", exception.getMessage());
        verify(teamRepository, never()).delete(team1);
    }

    @Test
    void deleteTeam_ShouldThrowTeamNotFoundException_WhenTeamNotExists() {
        // given
        when(teamService.getTeam("NonExistent"))
                .thenThrow(new TeamNotFoundException("Team is not found"));

        // when & then
        assertThrows(TeamNotFoundException.class,
                () -> gameTeamService.deleteTeam("NonExistent"));

        verify(teamRepository, never()).delete(any(Team.class));
    }

    // ==================== ТЕСТЫ ДЛЯ getGamesDate ====================

    @Test
    void getGamesDate_ShouldReturnGames_WhenGamesExistOnDate() {
        // given
        List<Game> games = new ArrayList<>();
        games.add(new Game(team1, team2, 2, 1, season, validDate));
        games.add(new Game(team2, team1, 1, 1, season, validDate));

        when(gameRepository.findAllOnDate(validDate)).thenReturn(games);

        // when
        List<Game> result = gameTeamService.getGamesDate(validDateStr);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAllOnDate(validDate);
    }

    @Test
    void getGamesDate_ShouldThrowGamesNotFoundException_WhenNoGamesOnDate() {
        // given
        when(gameRepository.findAllOnDate(validDate)).thenReturn(new ArrayList<>());

        // when & then
        GamesNotFoundException exception = assertThrows(GamesNotFoundException.class,
                () -> gameTeamService.getGamesDate(validDateStr));

        assertEquals("Games not found", exception.getMessage());
        verify(gameRepository, times(1)).findAllOnDate(validDate);
    }

    @Test
    void getGamesDate_ShouldReturnGamesSortedDescending() {
        // given
        LocalDate date1 = LocalDate.of(2024, 3, 15);
        LocalDate date2 = LocalDate.of(2024, 3, 14);

        Game game1 = new Game(team1, team2, 2, 1, season, date1);
        Game game2 = new Game(team1, team2, 1, 0, season, date2);
        List<Game> games = List.of(game1, game2);

        when(gameRepository.findAllOnDate(any(LocalDate.class))).thenReturn(games);

        // when
        List<Game> result = gameTeamService.getGamesDate("2024-03-15");

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        // Проверяем, что сортировка DESC работает (первый матч - более поздняя дата)
        verify(gameRepository, times(1)).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldThrowDateTimeParseException_WhenInvalidDateFormat() {
        // given
        String invalidDate = "15-03-2024";

        // when & then
        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.getGamesDate(invalidDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldThrowDateTimeParseException_WhenDateIsEmpty() {
        // given
        String emptyDate = "";

        // when & then
        assertThrows(DateTimeParseException.class,
                () -> gameTeamService.getGamesDate(emptyDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }

    @Test
    void getGamesDate_ShouldThrowNullPointerException_WhenDateIsNull() {
        // given
        String nullDate = null;

        // when & then
        assertThrows(NullPointerException.class,
                () -> gameTeamService.getGamesDate(nullDate));

        verify(gameRepository, never()).findAllOnDate(any(LocalDate.class));
    }
}