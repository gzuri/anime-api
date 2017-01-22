package com.goranzuri.anime.services;

import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.entities.Anime;

import java.util.*;

/**
 * Created by gzuri on 21.01.2017..
 */
public class AnimeUpdateService {
    private AnimeDAO animeDAO;
    private List<Anime> animeInSystem;
    private List<Anime> recordedAnimeOnStorage;
    private int storageId;

    AnimeUpdateService(AnimeDAO animeDAO){

        this.animeDAO = animeDAO;
    }

    public void updateListBasedOnStorageContent(List<String> currentAnimeOnStorage, int storageId){
        this.storageId = storageId;
        recordedAnimeOnStorage = animeDAO.findAllOnStorage(storageId);
        animeInSystem = animeDAO.findAll();


    }

    void filterNewlyAnime(List<Anime> recordedAnimeOnStorage, List<String> currentAnimeOnStorage){
        Map<String, Integer> recordedAnimeOnStorageMap = new HashMap<String, Integer>();
        for (Anime recordedAnimeOnStorageItem : recordedAnimeOnStorage){
            recordedAnimeOnStorageMap.put(recordedAnimeOnStorageItem.getNameOnDisk(), recordedAnimeOnStorageItem.getAnimeId());
        }

        List<String> filteredAnimeOnStorage = new ArrayList<String>();

        for (String currentAnimeOnStorageItem : currentAnimeOnStorage){
            if (recordedAnimeOnStorageMap.containsKey(currentAnimeOnStorageItem)){
                recordedAnimeOnStorageMap.remove(currentAnimeOnStorageItem);
            }else{
                filteredAnimeOnStorage.add(currentAnimeOnStorageItem);
            }
        }

        currentAnimeOnStorage = filteredAnimeOnStorage;
    }

    void addAnimeOnStorage(String animeNameOnStorage, int storageId){

    }

    void removeThoseNotOnStorage(Map<String, Integer> recordedAnimeOnStorage, List<String> currentAnimeOnStorage){

    }

}
