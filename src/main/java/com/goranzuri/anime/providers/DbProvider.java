package com.goranzuri.anime.providers;

import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 11/10/2017.
 */
public interface DbProvider {
    public List<Anime> get();
    public void save(List<Anime> animes);
    public Anime get(UUID animeId) throws AnimeNotFoundException;

}
