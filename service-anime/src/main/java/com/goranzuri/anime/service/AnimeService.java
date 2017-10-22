package com.goranzuri.anime.service;

import com.goranzuri.anime.AnimeApiConfiguration;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;
import com.goranzuri.anime.providers.DbProvider;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gzuri on 14/08/2017.
 */
public class AnimeService {
    private final AnimeApiConfiguration configuration;
    private final DbProvider dbProvider;
    private final AniDbService aniDbService;

    @Inject
    public AnimeService(AnimeApiConfiguration configuration, DbProvider dbProvider){
        this.configuration = configuration;

        this.dbProvider = dbProvider;
        aniDbService = new AniDbService();
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
        //replace trailing slash

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



    public void syncDrive(List<String> namesOnDisk, String storage){
        List<Anime> dbAnimes = getOnStorage(storage);
        Set<UUID> processedAnime = new HashSet<>();

        for(String nameOnDisk : namesOnDisk)
            nameOnDisk = nameOnDisk.replace("/$", "");

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
                dbProvider.update(anime);
            }
        }
    }

    private List<Anime> getOnStorage(String storage){
        return dbProvider.get().stream()
                .filter(x -> x.getStorage() != null && x.getStorage().contains(storage))
                .collect(Collectors.toList());
    }


    public void fillAniDbData(){
        List<Anime> dbAnimeWithoutAniDbReference = findAnimeWithoutAniDbReference();

        for (Anime anime: dbAnimeWithoutAniDbReference){
            try{
                Integer aniDbCode = aniDbService.getAnidbCode(anime.getName());
                anime.setAnidbCode(aniDbCode.toString());
                dbProvider.update(anime);

            }catch (Exception ex){

            }
        }
    }

    private List<Anime> findAnimeWithoutAniDbReference(){
        return dbProvider.get().stream()
                .filter(x -> x.getAnidbCode() == null || x.getAnidbCode().isEmpty())
                .collect(Collectors.toList());
    }

    public void fillThumbnails(){
        List<Anime> dbAnimeWithoutThumbnail = findAnimeWithoutThumbnail();

        for (Anime anime: dbAnimeWithoutThumbnail){
            try {
                String thumbnailUrl = aniDbService.getThumbnailUrl(anime.getAnidbCode());

                anime.setThumbnail(pullThumbnail(anime, thumbnailUrl));
                dbProvider.update(anime);

            }catch (Exception ex){

            }
        }

    }

    private List<Anime> findAnimeWithoutThumbnail(){
        return dbProvider.get().stream()
                .filter(x -> x.getAnidbCode() != null && x.getThumbnail() == null)
                .collect(Collectors.toList());
    }

    private String pullThumbnail(Anime anime, String thumbnailUrl) throws IOException {

        String thumbName = anime.getAnimeId().toString() + ".jpg";

        try(InputStream in = new URL(thumbnailUrl).openStream()){
            Files.copy(in, Paths.get(configuration.getImagesLocation() + thumbName));
        }

        return thumbName;
    }

}
