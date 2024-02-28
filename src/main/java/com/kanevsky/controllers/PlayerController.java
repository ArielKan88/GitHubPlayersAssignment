package com.kanevsky.controllers;

import com.kanevsky.entities.PlayerEntity;
import com.kanevsky.services.IPlayerService;
import com.kanevsky.views.PlayerView;
import com.kanevsky.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    // GET /api/players
    @GetMapping("/")
    public List<PlayerView> getAllPlayers() {
        List<PlayerEntity> allPlayers = playerService.getAllPlayers();
        return allPlayers.stream().map(PlayerMapper.INSTANCE::toView).collect(Collectors.toList());
    }

    // GET /api/players/{playerID}
    @GetMapping("/{playerId}")
    public PlayerView getPlayerById(@PathVariable String playerId) {
        return playerService.getPlayerById(playerId).map(PlayerMapper.INSTANCE::toView).orElse(null);
    }
}
