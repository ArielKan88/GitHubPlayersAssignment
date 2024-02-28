package com.kanevsky.mappers;

import com.kanevsky.entities.PlayerEntity;
import com.kanevsky.views.PlayerView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerView toView(PlayerEntity album);
}


























