package com.example.tournamentTable.Exception;

public class PlayerNotInTeamException extends RuntimeException{
    public PlayerNotInTeamException(String message){
        super(message);
    }
}
