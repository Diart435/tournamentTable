package com.example.tournamentTable.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerTeamDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String title;
}
