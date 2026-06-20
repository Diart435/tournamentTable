package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.TeamDTO;
import com.example.tournamentTable.DTO.TeamResponse;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Mapper.TeamMapper;
import com.example.tournamentTable.Service.GameTeamService;
import com.example.tournamentTable.Service.TeamService;
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
public class TeamController {
    private final TeamService teamService;
    private final GameTeamService gameTeamService;
    private final TeamMapper teamMapper;

    @GetMapping("/public/get/table")
    public ResponseEntity<List<TeamResponse>> getTable(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamResponse> teamResponses = teams.stream().map(teamMapper::toTeamResponse).toList();
        return ResponseEntity.ok(teamResponses);
    }

    @PostMapping("/private/add/team")
    public ResponseEntity<Void> addTeam(@Valid @RequestBody TeamDTO teamDTO){
        teamService.addTeam(teamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/private/delete/team")
    public ResponseEntity<Void> deleteTeam(@Valid @RequestBody TeamDTO teamDTO){
        gameTeamService.deleteTeam(teamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
