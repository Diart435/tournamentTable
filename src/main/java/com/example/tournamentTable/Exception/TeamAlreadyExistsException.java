package com.example.tournamentTable.Exception;

public class TeamAlreadyExistsException extends RuntimeException{
    public TeamAlreadyExistsException(String message){
        super(message);
    }
}
