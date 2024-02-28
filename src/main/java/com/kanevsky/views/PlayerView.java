package com.kanevsky.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerView {
    @JsonProperty(value = "playerID")
    private String playerID_;
    @JsonProperty(value = "birthYear")
    private Integer birthYear_;
    @JsonProperty(value = "birthMonth")
    private Integer birthMonth_;
    @JsonProperty(value = "birthDay")
    private Integer birthDay_;
    @JsonProperty(value = "birthCountry")
    private String birthCountry_;
    @JsonProperty(value = "birthState")
    private String birthState_;
    @JsonProperty(value = "birthCity")
    private String birthCity_;
    @JsonProperty(value = "deathYear")
    private Integer deathYear_;
    @JsonProperty(value = "deathMonth")
    private Integer deathMonth_;
    @JsonProperty(value = "deathDay")
    private Integer deathDay_;
    @JsonProperty(value = "deathCountry")
    private String deathCountry_;
    @JsonProperty(value = "deathState")
    private String deathState_;
    @JsonProperty(value = "deathCity")
    private String deathCity_;
    @JsonProperty(value = "nameFirst")
    private String nameFirst_;
    @JsonProperty(value = "nameLast")
    private String nameLast_;
    @JsonProperty(value = "nameGiven")
    private String nameGiven_;
    @JsonProperty(value = "weight")
    private Integer weight_;
    @JsonProperty(value = "height")
    private Integer height_;
    @JsonProperty(value = "bats")
    private String bats_;
    @JsonProperty(value = "throws")
    private String throws_;
    @JsonProperty(value = "debut")
    private LocalDate debut_;
    @JsonProperty(value = "finalGame")
    private LocalDate finalGame_;
    @JsonProperty(value = "retroID")
    private String retroID_;
    @JsonProperty(value = "bbrefID")
    private String bbrefID_;
}
