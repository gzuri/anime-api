package com.goranzuri.anime.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 20.01.2017..
 */
public class Anime {

    @JsonProperty
    private UUID animeId;
    @JsonProperty
    private String name;
    @JsonProperty
    private Long createdOn;
    @JsonProperty
    private Date updatedOn;

    @JsonProperty
    @NotEmpty
    private String nameOnDisk;

    @JsonProperty
    private String thumbnail;
    @JsonProperty
    private String anidbCode;
    @JsonProperty
    private List<String> storage;

    public Anime() {
    }

    public UUID getAnimeId() {
        return animeId;
    }

    public void setAnimeId(UUID animeId) {
        this.animeId = animeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }
}
