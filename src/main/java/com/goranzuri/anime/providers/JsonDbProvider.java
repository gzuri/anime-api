package com.goranzuri.anime.providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goranzuri.anime.AnimeApiConfiguration;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 10/10/2017.
 */
public class JsonDbProvider implements DbProvider {

    private final AnimeApiConfiguration configuration;

    @Inject
    public JsonDbProvider(AnimeApiConfiguration configuration){
        this.configuration = configuration;
    }


    //todo: extract to provider
    public List<Anime> get(){
        ObjectMapper mapper = new ObjectMapper();

        List<Anime> animes = null;
        try {
            animes = mapper.readValue(new File(configuration.getDbLocation()), new TypeReference<List<Anime>>(){} );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animes;
    }

    public Anime get(UUID animeId) throws AnimeNotFoundException {

        for (Anime anime: get()){
            if (anime.getAnimeId().equals(animeId))
                return anime;
        }
        throw new AnimeNotFoundException(animeId.toString());
    }

    //todo: extract to provider
    public void save(List<Anime> animes){
        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in file
        try {
            mapper.writeValue(new File(configuration.getDbLocation()), animes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Anime update(Anime anime){
        List<Anime> dbAnimes = get();

        Anime dbAnime = dbAnimes.stream()
                .filter(x -> x.getAnimeId().equals(anime.getAnimeId()))
                .findFirst()
                .get();

        dbAnime.setAnidbCode(anime.getAnidbCode());
        dbAnime.setStorage(anime.getStorage());
        dbAnime.setThumbnail(anime.getThumbnail());

        save(dbAnimes);

        return dbAnime;
    }

}
