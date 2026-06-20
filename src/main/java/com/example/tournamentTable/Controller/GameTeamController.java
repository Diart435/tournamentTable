package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.GameDTO;
import com.example.tournamentTable.DTO.GameResponse;
import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Mapper.GameMapper;
import com.example.tournamentTable.Service.GameTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameTeamController {
    private final GameTeamService gameTeamService;
    private final GameMapper gameMapper;

    @Operation(
            summary = "Get a list of games",
            description = "Returns a list of tournament's games"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of games"),
            @ApiResponse(responseCode = "404", description = "Empty games list")
    })
    @GetMapping("/public/get/games")
    public ResponseEntity<List<GameResponse>> getGames(){
        List<Game> games = gameTeamService.getAllGames();
        if(!games.isEmpty()) {
            List<GameResponse> gameResponses = games.stream().map(gameMapper::toGameResponse).toList();
            return ResponseEntity.ok(gameResponses);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get a team's games list",
            description = "Returns a team's games"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of team's games"),
            @ApiResponse(responseCode = "404", description = "Empty team's games list")
    })
    @GetMapping("/public/get/team/{title}")
    public ResponseEntity<List<GameResponse>> getGamesForTeam(@PathVariable String title){
        List<Game> games = gameTeamService.getGamesForTeam(title);
        List<GameResponse> gameResponses = games.stream().map(gameMapper::toGameResponse).toList();
        return ResponseEntity.ok(gameResponses);
    }

    @Operation(
            summary = "Create a game",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created"),
            @ApiResponse(responseCode = "404", description = "Team is not found")
    })
    @PostMapping("/private/add/game")
    public ResponseEntity<Void> addGame(@Valid @RequestBody GameDTO gameDTO) {
        gameTeamService.addGame(gameDTO.getTeam1(), gameDTO.getTeam2(), gameDTO.getScore1(), gameDTO.getScore2());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
