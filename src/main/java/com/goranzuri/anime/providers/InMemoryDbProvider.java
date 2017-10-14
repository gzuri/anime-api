package com.goranzuri.anime.providers;

import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 11/10/2017.
 */
public class InMemoryDbProvider implements DbProvider {
    private List<Anime> animes;

    public InMemoryDbProvider(){
        animes = new ArrayList<>();
    }

    @Override
    public List<Anime> get() {
        return this.animes;
    }

    @Override
    public void save(List<Anime> animes) {
        this.animes = animes;
    }

    @Override
    public Anime get(UUID animeId) throws AnimeNotFoundException {
        return this.animes.stream().filter(e -> e.getAnimeId().equals(animeId)).findFirst().get();
    }
}
