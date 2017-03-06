package com.goranzuri.anime.services;

import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.StorageNotFoundException;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gzuri on 21.01.2017..
 */
public class AnimeUpdateService {
    private AnimeDAO animeDAO;
    private List<Anime> animeInSystem;
    private List<Anime> recordedAnimeOnStorage;
    private int storageId;

    public AnimeUpdateService(AnimeDAO animeDAO, int storageId){

        this.animeDAO = animeDAO;
        this.storageId = storageId;
    }

    public void updateListBasedOnStorageContent(List<String> currentAnimeOnStorage) throws StorageNotFoundException {
        animeInSystem = animeDAO.findAll();
        recordedAnimeOnStorage = animeDAO.findAllOnStorage(storageId);

        processNewAnime(currentAnimeOnStorage);
    }

    void processNewAnime(List<String> currentAnimeOnStorage) throws StorageNotFoundException {
        List<String> existingAnimeOnStorage = recordedAnimeOnStorage.stream()
                .map(x->x.getNameOnDisk()).collect(Collectors.toList());

        List<String> tempCurrentListOnStorage = new ArrayList<>(currentAnimeOnStorage);
        tempCurrentListOnStorage.removeAll(existingAnimeOnStorage);

        for (String newItem : tempCurrentListOnStorage){
            addNewAnime(newItem);
        }
    }

    void addNewAnime(String nameOnDisk) throws StorageNotFoundException {
        Optional<Anime> localAnime = animeInSystem.stream()
                .filter(x -> x.getNameOnDisk().equals(nameOnDisk))
                .findFirst();

        Integer animeId = null;
        if (!localAnime.isPresent()){
            Anime insertAnime = new Anime();
            insertAnime.setDateAdded(new Date());
            insertAnime.setNameOnDisk(nameOnDisk);
            insertAnime.setName(nameOnDisk);

            animeDAO.create(insertAnime);
            animeId = insertAnime.getAnimeId();
        }else{
            animeId = localAnime.get().getAnimeId();
        }

        saveLocationOfAnime(animeId.intValue());

    }

    void saveLocationOfAnime(int animeId) throws StorageNotFoundException {
        animeDAO.saveOnStorage(animeId, this.storageId);
    }

    public static List<String> filterNewNames(List<String> oldList, List<String> newList){

        List<String> filteredNewStrings = new ArrayList<String>();

        for (String currentItem : newList){
            if (!oldList.contains(currentItem)){
                filteredNewStrings.add(currentItem);
            }
        }

        return filteredNewStrings;
    }


    static List<String> filterNewAnime(List<Anime> recordedAnimeOnStorage, List<String> currentAnimeOnStorage){
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

        return filteredAnimeOnStorage;
    }

    void addAnimeOnStorage(String animeNameOnStorage, int storageId){

    }

    void removeThoseNotOnStorage(Map<String, Integer> recordedAnimeOnStorage, List<String> currentAnimeOnStorage){

    }

}
