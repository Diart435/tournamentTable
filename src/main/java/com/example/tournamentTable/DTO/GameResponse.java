package com.example.tournamentTable.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GameResponse {
    private String team1;
    private String team2;
    private int score1;
    private int score2;
    private List<String> playersTeam1;
    private List<String> playersTeam2;
    private String season;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate matchDate;
}
