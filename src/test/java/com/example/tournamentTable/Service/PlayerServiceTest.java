package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Exception.PlayerAlreadyExistsException;
import com.example.tournamentTable.Exception.PlayerNotFoundException;
import com.example.tournamentTable.Repository.PlayerRepository;
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
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayer_ShouldReturnPlayer_WhenPlayerExists() {
        // given
        String name = "Messi";
        Player expectedPlayer = new Player(name);
        expectedPlayer.setId(UUID.randomUUID());

        when(playerRepository.findByName(name)).thenReturn(Optional.of(expectedPlayer));

        // when
        Player actualPlayer = playerService.getPlayer(name);

        // then
        assertNotNull(actualPlayer);
        assertEquals(name, actualPlayer.getName());
        verify(playerRepository, times(1)).findByName(name);
    }

    @Test
    void getPlayer_ShouldThrowPlayerNotFoundException_WhenPlayerNotExists() {
        // given
        String name = "Unknown";
        when(playerRepository.findByName(name)).thenReturn(Optional.empty());

        // when & then
        assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayer(name));
        verify(playerRepository, times(1)).findByName(name);
    }

    @Test
    void getAllPlayers_ShouldReturnAllPlayers() {
        // given
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        Player player3 = new Player("Player 3");

        when(playerRepository.findAllWithTeam()).thenReturn(List.of(player1, player2));
        when(playerRepository.findAllWithoutTeam()).thenReturn(List.of(player3));

        // when
        List<Player> players = playerService.getAllPlayers();

        // then
        assertNotNull(players);
        assertEquals(3, players.size());
        verify(playerRepository, times(1)).findAllWithTeam();
        verify(playerRepository, times(1)).findAllWithoutTeam();
    }

    @Test
    void addPlayer_ShouldSavePlayer_WhenPlayerNotExists() {
        // given
        String name = "New Player";
        when(playerRepository.existsByName(name)).thenReturn(false);

        // when
        playerService.addPlayer(name);

        // then
        verify(playerRepository, times(1)).existsByName(name);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void addPlayer_ShouldThrowPlayerAlreadyExistsException_WhenPlayerExists() {
        // given
        String name = "Existing Player";
        when(playerRepository.existsByName(name)).thenReturn(true);

        // when & then
        assertThrows(PlayerAlreadyExistsException.class, () -> playerService.addPlayer(name));
        verify(playerRepository, times(1)).existsByName(name);
        verify(playerRepository, never()).save(any(Player.class));
    }
}