package com.example.tournamentTable.Exception;

public class PlayerAlreadyExistsException extends RuntimeException{
    public PlayerAlreadyExistsException(String message){
        super(message);
    }
}
