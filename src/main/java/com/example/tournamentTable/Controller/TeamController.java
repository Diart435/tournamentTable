package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerToTeamDTO;
import com.example.tournamentTable.DTO.TeamDTO;
import com.example.tournamentTable.Service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }
    @PostMapping("/private/add/team")
    public ResponseEntity<Void> addTeam(@Valid @RequestBody TeamDTO teamDTO){
        teamService.addTeam(teamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/private/add/toteam")
    public ResponseEntity<Void> addToTeam(@Valid @RequestBody PlayerToTeamDTO playerToTeamDTO){
        teamService.addPlayerToTeam(playerToTeamDTO.getTitle(), playerToTeamDTO.getName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
