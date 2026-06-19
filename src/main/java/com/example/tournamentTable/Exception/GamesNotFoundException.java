package com.example.tournamentTable.Exception;

public class GamesNotFoundException extends RuntimeException{
    public GamesNotFoundException(String message){
        super(message);
    }
}
