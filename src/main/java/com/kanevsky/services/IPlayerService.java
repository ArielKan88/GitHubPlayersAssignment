package com.kanevsky.services;

import com.kanevsky.entities.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface IPlayerService {
    List<PlayerEntity> getAllPlayers();

    Optional<PlayerEntity> getPlayerById(String playerId);
}
