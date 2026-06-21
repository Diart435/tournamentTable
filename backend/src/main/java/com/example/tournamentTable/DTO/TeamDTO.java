package com.example.tournamentTable.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamDTO {
    @NotBlank
    private String title;

}
