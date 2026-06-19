package com.example.tournamentTable.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerDTO {
    @NotBlank
    String name;
}
