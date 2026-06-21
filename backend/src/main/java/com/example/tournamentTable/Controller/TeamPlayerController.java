package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerTeamDTO;
import com.example.tournamentTable.DTO.TransferDTO;
import com.example.tournamentTable.Service.TeamPlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TeamPlayerController {
    private final TeamPlayerService teamPlayerService;

    @Operation(
            summary = "Add player to team",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player  added to team"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @PostMapping("/private/add/toteam")
    public ResponseEntity<Void> addToTeam(@Valid @RequestBody PlayerTeamDTO playerTeamDTO){
        teamPlayerService.addPlayerToTeam(playerTeamDTO.getName(), playerTeamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Delete player from team",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player deleted from team"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "404", description = "Player not found"),
            @ApiResponse(responseCode = "404", description = "Player is not in team")
    })
    @PatchMapping("/private/delete/fromteam")
    public ResponseEntity<Void> deleteFromTeam(@Valid @RequestBody PlayerTeamDTO playerTeamDTO){
        teamPlayerService.deletePlayerFromTeam(playerTeamDTO.getName(), playerTeamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Transfer a player between a teams",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player transfered"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "404", description = "Player not found"),
            @ApiResponse(responseCode = "404", description = "Player is not in team")
    })
    @PatchMapping("/private/update/transfer")
    public ResponseEntity<Void> transferPlayer(@Valid @RequestBody TransferDTO transferDTO){
        teamPlayerService.transferPlayer(transferDTO.getName(), transferDTO.getTeam1(), transferDTO.getTeam2());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a player",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player deleted"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @DeleteMapping("/private/delete/player")
    public ResponseEntity<Void> deletePlayer(@Valid @RequestBody PlayerTeamDTO playerTeamDTO){
        teamPlayerService.deletePlayer(playerTeamDTO.getName(), playerTeamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
