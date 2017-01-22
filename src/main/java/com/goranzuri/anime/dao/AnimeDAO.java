package com.goranzuri.anime.dao;

import com.goranzuri.anime.entities.Anime;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by gzuri on 20.01.2017..
 */
public class AnimeDAO extends AbstractDAO<Anime> {
    public AnimeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Anime> findAll(){
        List<Anime> a = list(namedQuery("anime.findAll"));
        return a;
    }

    public List<Anime> findAllOnStorage(int storageId){
        return list(namedQuery("anime.findAllOnDrive"));
    }
}
