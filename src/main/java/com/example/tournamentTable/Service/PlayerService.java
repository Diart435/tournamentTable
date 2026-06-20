package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Exception.PlayerNotFoundException;
import com.example.tournamentTable.Mapper.PlayerMapper;
import com.example.tournamentTable.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<Player> getAllPlayers(){
        List<Player> playersWithTeam = playerRepository.findAllWithTeam();
        List<Player> playersWithoutTeam = playerRepository.findAllWithoutTeam();
        playersWithTeam.addAll(playersWithoutTeam);
        return playersWithTeam;
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
