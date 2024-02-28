package com.kanevsky.services;

import com.kanevsky.entities.PlayerEntity;
import com.kanevsky.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService implements IPlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<PlayerEntity> getPlayerById(String playerId) {
        return playerRepository.findById(playerId);
    }
}
