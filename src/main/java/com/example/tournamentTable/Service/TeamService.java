package com.example.tournamentTable.Service;

import com.example.tournamentTable.Entity.Player;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Exception.TeamAlreadyExistsException;
import com.example.tournamentTable.Exception.TeamHaveGamesException;
import com.example.tournamentTable.Exception.TeamNotFoundException;
import com.example.tournamentTable.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public Team getTeam(String title){
        Optional<Team> existing = teamRepository.findByTitle(title);
        if(existing.isPresent()) {
            return existing.get();
        }
        else{
            throw new TeamNotFoundException("Team is not found");
        }
    }

    @Transactional(readOnly = true)
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    @Transactional
    public void addTeam(String title){
        if(teamRepository.existsByTitle(title)){
            throw new TeamAlreadyExistsException("Team already exists");
        }
        else{
            Team team = new Team(title);
            teamRepository.save(team);
        }
    }
}
