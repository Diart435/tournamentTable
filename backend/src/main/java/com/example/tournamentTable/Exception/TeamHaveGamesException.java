package com.example.tournamentTable.Exception;

public class TeamHaveGamesException extends RuntimeException{
    public TeamHaveGamesException(String message){
        super(message);
    }
}
