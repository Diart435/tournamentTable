package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Exception.PlayerNotFoundException;
import com.example.tournamentTable.Repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

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

    @Transactional
    public void addPlayer(String name){
        Player player = new Player(name);
        playerRepository.save(player);
    }

    @Transactional
    public void deletePlayer(String name){
        //TODO сделать логику удаления игрока внутри команды и в целом удаления
    }
}
