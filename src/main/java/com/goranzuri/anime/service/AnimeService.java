package com.goranzuri.anime.service;

import com.goranzuri.anime.db.dao.AnimeDAO;
import com.goranzuri.anime.db.entities.Anime;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by gzuri on 14/08/2017.
 */
public class AnimeService {
    private final AnimeDAO animeDAO;

    @Inject
    public AnimeService(AnimeDAO animeDAO){
        this.animeDAO = animeDAO;
    }

    public List<Anime> get(){
        return animeDAO.findAll();
    }

    public Anime get(Integer animeId){
        return animeDAO.get(animeId);
    }

}
