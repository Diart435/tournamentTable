package com.example.tournamentTable.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team2_id")
    private Team team2;

    private int score1;

    private int score2;

    @Column(nullable = false)
    private String season;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    public Game(Team team1, Team team2, int score1, int score2, String season, LocalDate matchDate){
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.season = season;
        this.matchDate = matchDate;
    }
}
