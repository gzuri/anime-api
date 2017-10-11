package com.goranzuri.anime.service;

import com.goranzuri.anime.AnimeApiConfiguration;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.providers.DbProvider;
import com.goranzuri.anime.providers.JsonDbProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 14/08/2017.
 */
public class AnimeService {
    private final AnimeApiConfiguration configuration;
    private final DbProvider dbProvider;

    @Inject
    public AnimeService(AnimeApiConfiguration configuration){
        this.configuration = configuration;
        this.dbProvider = new JsonDbProvider(configuration);
    }

    public List<Anime> get(){
        return dbProvider.get();
    }

    public Anime get(UUID animeId){
        throw new NotImplementedException();
    }

    public Anime add(Anime newAnime) {
        List<Anime> animes = dbProvider.get();

        newAnime.setAnimeId(UUID.randomUUID());
        newAnime.setCreatedOn(Instant.now().getEpochSecond());
        animes.add(newAnime);

        dbProvider.save(animes);
        return newAnime;
    }


}
