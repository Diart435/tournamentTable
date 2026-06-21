package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.PlayerNotInTeamException;
import com.example.tournamentTable.Repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamPlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private TeamPlayerService teamPlayerService;

    @Test
    void addPlayerToTeam_ShouldAddPlayerToTeam_WhenValid() {
        String playerName = "Messi";
        String teamTitle = "FC Barcelona";

        Player player = new Player(playerName);
        player.setId(UUID.randomUUID());

        Team team = new Team(teamTitle);
        team.setId(UUID.randomUUID());
        team.setPlayers(new ArrayList<>());

        when(playerService.getPlayer(playerName)).thenReturn(player);
        when(teamService.getTeam(teamTitle)).thenReturn(team);

        teamPlayerService.addPlayerToTeam(playerName, teamTitle);

        assertTrue(team.getPlayers().contains(player));
        assertEquals(team, player.getTeam());
        verify(playerService, times(1)).getPlayer(playerName);
        verify(teamService, times(1)).getTeam(teamTitle);
    }

    @Test
    void removeFromTeam_ShouldRemovePlayer_WhenPlayerInTeam() {
        Player player = new Player("Messi");
        player.setId(UUID.randomUUID());

        Team team = new Team("FC Barcelona");
        team.setId(UUID.randomUUID());
        team.setPlayers(new ArrayList<>());
        team.getPlayers().add(player);
        player.setTeam(team);

        teamPlayerService.removeFromTeam(player, team);

        assertFalse(team.getPlayers().contains(player));
        assertNull(player.getTeam());
    }

    @Test
    void removeFromTeam_ShouldThrowPlayerNotInTeamException_WhenPlayerNotInTeam() {
        Player player = new Player("Messi");
        player.setId(UUID.randomUUID());

        Team team = new Team("FC Barcelona");
        team.setId(UUID.randomUUID());
        team.setPlayers(new ArrayList<>());

        assertThrows(PlayerNotInTeamException.class,
                () -> teamPlayerService.removeFromTeam(player, team));
    }

    @Test
    void deletePlayerFromTeam_ShouldRemovePlayer_WhenValid() {
        String playerName = "Messi";
        String teamTitle = "FC Barcelona";

        Player player = new Player(playerName);
        player.setId(UUID.randomUUID());

        Team team = new Team(teamTitle);
        team.setId(UUID.randomUUID());
        team.setPlayers(new ArrayList<>());
        team.getPlayers().add(player);
        player.setTeam(team);

        when(playerService.getPlayer(playerName)).thenReturn(player);
        when(teamService.getTeam(teamTitle)).thenReturn(team);

        teamPlayerService.deletePlayerFromTeam(playerName, teamTitle);

        assertFalse(team.getPlayers().contains(player));
        assertNull(player.getTeam());
    }

    @Test
    void transferPlayer_ShouldTransferPlayer_WhenValid() {
        String playerName = "Messi";
        String fromTeamTitle = "FC Barcelona";
        String toTeamTitle = "PSG";

        Player player = new Player(playerName);
        player.setId(UUID.randomUUID());

        Team fromTeam = new Team(fromTeamTitle);
        fromTeam.setId(UUID.randomUUID());
        fromTeam.setPlayers(new ArrayList<>());
        fromTeam.getPlayers().add(player);
        player.setTeam(fromTeam);

        Team toTeam = new Team(toTeamTitle);
        toTeam.setId(UUID.randomUUID());
        toTeam.setPlayers(new ArrayList<>());

        when(playerService.getPlayer(playerName)).thenReturn(player);
        when(teamService.getTeam(fromTeamTitle)).thenReturn(fromTeam);
        when(teamService.getTeam(toTeamTitle)).thenReturn(toTeam);

        teamPlayerService.transferPlayer(playerName, fromTeamTitle, toTeamTitle);

        assertFalse(fromTeam.getPlayers().contains(player));
        assertTrue(toTeam.getPlayers().contains(player));
        assertEquals(toTeam, player.getTeam());
    }

    @Test
    void deletePlayer_ShouldDeletePlayerFromTeam_WhenValid() {
        String playerName = "Messi";
        String teamTitle = "FC Barcelona";

        Player player = new Player(playerName);
        player.setId(UUID.randomUUID());

        Team team = new Team(teamTitle);
        team.setId(UUID.randomUUID());
        team.setPlayers(new ArrayList<>());
        team.getPlayers().add(player);
        player.setTeam(team);

        when(playerService.getPlayer(playerName)).thenReturn(player);
        when(teamService.getTeam(teamTitle)).thenReturn(team);

        teamPlayerService.deletePlayer(playerName, teamTitle);

        assertFalse(team.getPlayers().contains(player));
        assertNull(player.getTeam());
        verify(playerRepository, times(1)).delete(player);
    }
}