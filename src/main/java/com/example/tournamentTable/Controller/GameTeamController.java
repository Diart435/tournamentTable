package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.GameDTO;
import com.example.tournamentTable.DTO.GameResponse;
import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Mapper.GameMapper;
import com.example.tournamentTable.Service.GameTeamService;
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

    @GetMapping("/public/get/games")
    public ResponseEntity<List<GameResponse>> getGames(){
        List<Game> games = gameTeamService.getAllGames();
        List<GameResponse> gameResponses = games.stream().map(gameMapper::toGameResponse).toList();
        return ResponseEntity.ok(gameResponses);
    }

    @GetMapping("/public/get/team/{title}")
    public ResponseEntity<List<GameResponse>> getGamesForTeam(@PathVariable String title){
        List<Game> games = gameTeamService.getGamesForTeam(title);
        List<GameResponse> gameResponses = games.stream().map(gameMapper::toGameResponse).toList();
        return ResponseEntity.ok(gameResponses);
    }

    @PostMapping("/private/add/game")
    public ResponseEntity<Void> addGame(@Valid @RequestBody GameDTO gameDTO) {
        gameTeamService.addGame(gameDTO.getTeam1(), gameDTO.getTeam2(), gameDTO.getScore1(), gameDTO.getScore2());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
