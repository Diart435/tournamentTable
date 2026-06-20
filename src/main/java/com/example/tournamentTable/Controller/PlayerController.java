package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerDTO;
import com.example.tournamentTable.DTO.PlayerResponse;
import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Mapper.PlayerMapper;
import com.example.tournamentTable.Service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @GetMapping("/public/get/players")
    public ResponseEntity<List<PlayerResponse>> getPlayers(){
        List<Player> playerList = playerService.getAllPlayers();
        List<PlayerResponse> playerResponses = playerList.stream().map(playerMapper::toPlayerResponse).toList();
        return ResponseEntity.ok(playerResponses);
    }
    @PostMapping("/private/add/player")
    public ResponseEntity<Void> addPlayer(@Valid @RequestBody PlayerDTO playerDTO){
        playerService.addPlayer(playerDTO.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
