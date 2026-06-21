package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Exception.PlayerAlreadyExistsException;
import com.example.tournamentTable.Exception.PlayerNotFoundException;
import com.example.tournamentTable.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Player getPlayer(String name){
        Optional<Player> exists = playerRepository.findByName(name);
        if(exists.isPresent()){
            return exists.get();
        }
        else{
            throw new PlayerNotFoundException("Player is not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers(){
        List<Player> playersWithTeam = playerRepository.findAllWithTeam();
        List<Player> playersWithoutTeam = playerRepository.findAllWithoutTeam();
        List<Player> players = new ArrayList<>(playersWithTeam);
        players.addAll(playersWithoutTeam);
        return players;
    }

    @Transactional
    public void addPlayer(String name){
        if(!playerRepository.existsByName(name)) {
            Player player = new Player(name);
            playerRepository.save(player);
        }
        else{
            throw new PlayerAlreadyExistsException("Player already exists");
        }
    }
}
