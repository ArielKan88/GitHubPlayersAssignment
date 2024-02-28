package com.kanevsky.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "player", indexes = {@Index(name = "player_id_index", columnList = "playerID"), @Index(name = "retro_id_index", columnList = "retroID"), @Index(name = "bbref_id_index", columnList = "bbrefID")})
public class PlayerEntity {
    @Id
    @Column(name = "playerid")
    private String playerID_;
    @Column(name = "birth_year")
    private Integer birthYear_;
    @Column(name = "birth_month")
    private Integer birthMonth_;
    @Column(name = "birth_day")
    private Integer birthDay_;
    @Column(name = "birth_country")
    private String birthCountry_;
    @Column(name = "birth_state")
    private String birthState_;
    @Column(name = "birth_city")
    private String birthCity_;
    @Column(name = "death_year")
    private Integer deathYear_;
    @Column(name = "death_month")
    private Integer deathMonth_;
    @Column(name = "death_day")
    private Integer deathDay_;
    @Column(name = "death_country")
    private String deathCountry_;
    @Column(name = "death_state")
    private String deathState_;
    @Column(name = "death_city")
    private String deathCity_;
    @Column(name = "name_first")
    private String nameFirst_;
    @Column(name = "name_last")
    private String nameLast_;
    @Column(name = "name_given")
    private String nameGiven_;
    @Column(name = "weight")
    private Integer weight_;
    @Column(name = "height")
    private Integer height_;
    @Column(name = "bats")
    private String bats_;
    @Column(name = "throws")
    private String throws_;
    @Column(name = "debut")
    private LocalDate debut_;
    @Column(name = "final_game")
    private LocalDate finalGame_;
    @Column(name = "retroid")
    private String retroID_;
    @Column(name = "bbrefid")
    private String bbrefID_;
}
























