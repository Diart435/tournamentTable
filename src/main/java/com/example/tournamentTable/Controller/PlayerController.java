package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerDTO;
import com.example.tournamentTable.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping("/private/add/player")
    public ResponseEntity<Void> addPlayer(@Valid @RequestBody PlayerDTO playerDTO){
        playerService.addPlayer(playerDTO.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
