package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.TeamDTO;
import com.example.tournamentTable.DTO.TeamResponse;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Mapper.TeamMapper;
import com.example.tournamentTable.Service.GameTeamService;
import com.example.tournamentTable.Service.TeamService;
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
public class TeamController {
    private final TeamService teamService;
    private final GameTeamService gameTeamService;
    private final TeamMapper teamMapper;

    @Operation(
            summary = "Get a table of tournament",
            description = "Returns a list of teams' results"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all teams"),
            @ApiResponse(responseCode = "404", description = "Empty tournament's list")
    })
    @GetMapping("/public/get/table")
    public ResponseEntity<List<TeamResponse>> getTable(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamResponse> teamResponses = teams.stream().map(teamMapper::toTeamResponse).toList();
        return ResponseEntity.ok(teamResponses);
    }

    @Operation(
            summary = "Create a team",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team created"),
            @ApiResponse(responseCode = "409", description = "Team already exists")
    })
    @PostMapping("/private/add/team")
    public ResponseEntity<Void> addTeam(@Valid @RequestBody TeamDTO teamDTO){
        teamService.addTeam(teamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete a team",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team deleted"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "409", description = "Team have games")
    })
    @DeleteMapping("/private/delete/team")
    public ResponseEntity<Void> deleteTeam(@Valid @RequestBody TeamDTO teamDTO){
        gameTeamService.deleteTeam(teamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
