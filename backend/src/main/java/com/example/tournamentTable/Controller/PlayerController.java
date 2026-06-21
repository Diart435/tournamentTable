package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerDTO;
import com.example.tournamentTable.DTO.PlayerResponse;
import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Mapper.PlayerMapper;
import com.example.tournamentTable.Service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get a players' list",
            description = "Returns a players' list"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all players"),
            @ApiResponse(responseCode = "404", description = "Empty players' list")
    })
    @GetMapping("/public/get/players")
    public ResponseEntity<List<PlayerResponse>> getPlayers(){
        List<Player> playerList = playerService.getAllPlayers();
        if(!playerList.isEmpty()) {
            List<PlayerResponse> playerResponses = playerList.stream().map(playerMapper::toPlayerResponse).toList();
            return ResponseEntity.ok(playerResponses);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Create a player",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created"),
            @ApiResponse(responseCode = "409", description = "Player already exists")
    })
    @PostMapping("/private/add/player")
    public ResponseEntity<Void> addPlayer(@Valid @RequestBody PlayerDTO playerDTO){
        playerService.addPlayer(playerDTO.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
