package com.example.tournamentTable.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransferDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String team1;
    @NotBlank
    private String team2;
}
