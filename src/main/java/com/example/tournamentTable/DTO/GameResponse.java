package com.example.tournamentTable.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GameResponse {
    private String team1;
    private String team2;
    private int score1;
    private int score2;
    private List<String> playersTeam1;
    private List<String> playersTeam2;
}
