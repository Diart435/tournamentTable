package com.example.tournamentTable.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "team")
    private List<Player> players;

    private int teamScore;

    private int matches;

    private int wins;

    private int losses;

    private int draws;

    public Team(String title){
        this.title = title;
        this.players = new ArrayList<>();
        this.teamScore = 0;
        this.matches = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }
}
