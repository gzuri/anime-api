package com.goranzuri.anime.db.dao;

import com.goranzuri.anime.db.entities.Anime;
import com.goranzuri.anime.db.entities.AnimeStorage;
import com.goranzuri.anime.db.entities.Storage;
import com.goranzuri.anime.exceptions.StorageNotFoundException;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
        Query allAnimeOnStorageQuery = currentSession().createQuery("select anistor.anime from anime_storage anistor where anistor.storage.id = :storageId");
        allAnimeOnStorageQuery.setParameter("storageId", storageId);

        return allAnimeOnStorageQuery.list();
    }

    public Anime get(Integer animeId){
        return currentSession().get(Anime.class, (Serializable) Objects.requireNonNull(animeId));
    }

    public void create(Anime anime){
        currentSession().save(Objects.requireNonNull(anime));
    }

    public void update(Anime entity){
        currentSession().update(Objects.requireNonNull(entity));
    }

    public void saveOnStorage(int animeId, int storageId) throws StorageNotFoundException {

        Anime anime = currentSession().get(Anime.class, animeId);
        Storage storage = currentSession().get(Storage.class, storageId);
        if (storage == null)
            throw new StorageNotFoundException("There is no storage defined with storageId=" + storageId);
        AnimeStorage animeStorage = new AnimeStorage();
        animeStorage.setStorage(storage);
        animeStorage.setAnime(anime);

        currentSession().save(animeStorage);
    }
}
