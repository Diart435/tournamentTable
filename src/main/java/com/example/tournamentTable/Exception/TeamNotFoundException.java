package com.example.tournamentTable.Exception;

public class TeamNotFoundException extends RuntimeException{
    private String message;
    public TeamNotFoundException(String message){
        super(message);
    }
}
