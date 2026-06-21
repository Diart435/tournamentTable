package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.TeamAlreadyExistsException;
import com.example.tournamentTable.Exception.TeamNotFoundException;
import com.example.tournamentTable.Repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void getTeam_ShouldReturnTeam_WhenTeamExists() {
        String title = "FC Barcelona";
        Team expectedTeam = new Team();
        expectedTeam.setId(UUID.randomUUID());
        expectedTeam.setTitle(title);

        when(teamRepository.findByTitle(title)).thenReturn(Optional.of(expectedTeam));

        Team actualTeam = teamService.getTeam(title);

        assertNotNull(actualTeam);
        assertEquals(title, actualTeam.getTitle());
        verify(teamRepository, times(1)).findByTitle(title);
    }

    @Test
    void getTeam_ShouldThrowTeamNotFoundException_WhenTeamNotExists() {
        String title = "NonExistentTeam";
        when(teamRepository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> teamService.getTeam(title));
        verify(teamRepository, times(1)).findByTitle(title);
    }

    @Test
    void getAllTeams_ShouldReturnListOfTeams() {
        Team team1 = new Team();
        team1.setTitle("Team A");
        Team team2 = new Team();
        team2.setTitle("Team B");

        when(teamRepository.findAll()).thenReturn(List.of(team1, team2));

        List<Team> teams = teamService.getAllTeams();

        assertNotNull(teams);
        assertEquals(2, teams.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void addTeam_ShouldSaveTeam_WhenTeamNotExists() {
        String title = "New Team";
        when(teamRepository.existsByTitle(title)).thenReturn(false);

        teamService.addTeam(title);

        verify(teamRepository, times(1)).existsByTitle(title);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void addTeam_ShouldThrowTeamAlreadyExistsException_WhenTeamExists() {
        String title = "Existing Team";
        when(teamRepository.existsByTitle(title)).thenReturn(true);

        assertThrows(TeamAlreadyExistsException.class, () -> teamService.addTeam(title));
        verify(teamRepository, times(1)).existsByTitle(title);
        verify(teamRepository, never()).save(any(Team.class));
    }
}