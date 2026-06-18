package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Exception.PlayerNotFoundException;
import com.example.tournamentTable.Repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public Player getPlayer(String name){
        Optional<Player> exists = playerRepository.findByName(name);
        if(exists.isPresent()){
            return exists.get();
        }
        else{
            throw new PlayerNotFoundException("Player is not found");
        }
    }

}
