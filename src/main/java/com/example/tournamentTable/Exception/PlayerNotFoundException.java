package com.example.tournamentTable.Exception;

public class PlayerNotFoundException extends RuntimeException{
    private String message;
    public PlayerNotFoundException(String message) {
        super(message);
    }
}

