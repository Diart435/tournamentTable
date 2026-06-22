package com.example.tournamentTable.DTO;

import lombok.Data;

@Data
public class TeamResponse {
    private String title;
    private int teamScore;
    private int matches;
    private int wins;
    private int losses;
    private int draws;
}
