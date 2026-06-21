package com.example.tournamentTable.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class GameDTO {
    @NotBlank
    private String team1;
    @NotBlank
    private String team2;
    @NotNull
    private int score1;
    @NotNull
    private int score2;
    @NotBlank
    private String season;
    @NotBlank
    private String matchDate;
}
