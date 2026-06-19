package com.example.tournamentTable.Exception;

public class GamesNotFoundException extends RuntimeException{
    private String message;
    public GamesNotFoundException(String message){
        super(message);
    }
}
