package com.goranzuri.anime.service;

import com.goranzuri.anime.AnimeApiConfiguration;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;
import com.goranzuri.anime.providers.DbProvider;
import com.goranzuri.anime.providers.JsonDbProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gzuri on 14/08/2017.
 */
public class AnimeService {
    private final DbProvider dbProvider;

    @Inject
    public AnimeService(DbProvider dbProvider){
        this.dbProvider = dbProvider;
    }

    public List<Anime> get(){
        return dbProvider.get();
    }

    public Anime get(UUID animeId) throws AnimeNotFoundException {
        return dbProvider.get(animeId);
    }



    public Anime add(Anime newAnime) {
        List<Anime> animes = dbProvider.get();

        newAnime.setAnimeId(UUID.randomUUID());
        newAnime.setCreatedOn(Instant.now().getEpochSecond());
        animes.add(newAnime);

        if (newAnime.getName() == null || newAnime.getName().isEmpty()) {
            newAnime.setName(newAnime.getNameOnDisk());
        }

        dbProvider.save(animes);
        return newAnime;
    }


    public Anime merge(Anime anime){
        List<Anime> dbAnimes = dbProvider.get();

        Optional<Anime> dbAnimeResult = dbAnimes.stream()
                                .filter(e -> e.getNameOnDisk() != null && e.getNameOnDisk().equals(anime.getNameOnDisk()))
                                .findFirst();

        if (dbAnimeResult.isPresent()){
            for (String item: anime.getStorage()) {
                Anime dbAnime = dbAnimeResult.get();
                if (!dbAnime.getStorage().contains(item)) {
                    List<String> storage = new ArrayList<>(dbAnime.getStorage());
                    storage.add(item);
                    dbAnime.setStorage(storage);
                }
            }

        }else{
            return add(anime);
        }
        dbProvider.save(dbAnimes);

        return anime;
    }

    public Anime update(Anime anime){
        List<Anime> dbAnimes = dbProvider.get();

        Anime dbAnime = dbAnimes.stream()
                .filter(x -> x.getAnimeId().equals(anime.getAnimeId()))
                .findFirst()
                .get();

        dbAnime.setStorage(anime.getStorage());


        dbProvider.save(dbAnimes);

        return dbAnime;
    }


    public void syncDrive(List<String> namesOnDisk, String storage){
        List<Anime> dbAnimes = getOnStorage(storage);
        Set<UUID> processedAnime = new HashSet<>();

        for (String nameOnDisk: namesOnDisk){
            Optional<Anime> dbAnimeResult = dbAnimes.stream()
                    .filter(x -> x.getNameOnDisk().equals(nameOnDisk))
                    .findFirst();

            if (!dbAnimeResult.isPresent()){
                Anime newAnime = new Anime();
                newAnime.setNameOnDisk(nameOnDisk);
                newAnime.setStorage(Arrays.asList(storage));
                merge(newAnime);
            }else{
                processedAnime.add(dbAnimeResult.get().getAnimeId());
            }
        }

        //update removed anime from storage
        for(Anime anime: dbAnimes){
            if (!processedAnime.contains(anime.getAnimeId())){
                List<String> animeStorage = new ArrayList<>(anime.getStorage());
                animeStorage.remove(storage);
                anime.setStorage(animeStorage);
                update(anime);
            }
        }
    }

    private List<Anime> getOnStorage(String storage){
        return dbProvider.get().stream()
                .filter(x -> x.getStorage() != null && x.getStorage().contains(storage))
                .collect(Collectors.toList());
    }

}
