package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.PlayerTeamDTO;
import com.example.tournamentTable.DTO.TeamDTO;
import com.example.tournamentTable.DTO.TransferDTO;
import com.example.tournamentTable.Service.TeamPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamPlayerController {
    private final TeamPlayerService teamPlayerService;

    @PostMapping("/private/add/toteam")
    public ResponseEntity<Void> addToTeam(@Valid @RequestBody PlayerTeamDTO playerTeamDTO){
        teamPlayerService.addPlayerToTeam(playerTeamDTO.getName(), playerTeamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/private/delete/fromteam")
    public ResponseEntity<Void> deleteFromTeam(@Valid @RequestBody PlayerTeamDTO playerTeamDTO){
        teamPlayerService.deletePlayerFromTeam(playerTeamDTO.getName(), playerTeamDTO.getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/private/update/transfer")
    public ResponseEntity<Void> transferPlayer(@Valid @RequestBody TransferDTO transferDTO){
        teamPlayerService.transferPlayer(transferDTO.getName(), transferDTO.getTeam1(), transferDTO.getTeam2());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
