package com.goranzuri.anime.db.dao;

import com.goranzuri.anime.db.entities.Storage;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by gzuri on 20.01.2017..
 */
public class StorageDAO extends AbstractDAO<Storage> {
    public StorageDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Storage> findAll(){
        return list(namedQuery("storage.findAll"));
    }

}
