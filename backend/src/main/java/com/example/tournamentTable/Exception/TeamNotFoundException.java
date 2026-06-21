package com.example.tournamentTable.Exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(String message){
        super(message);
    }
}
