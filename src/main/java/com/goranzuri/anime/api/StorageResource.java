package com.goranzuri.anime.api;

import com.goranzuri.anime.dao.StorageDAO;
import com.goranzuri.anime.entities.Storage;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by gzuri on 20.01.2017..
 */
@Api
@Path("/storage")
@Produces(MediaType.APPLICATION_JSON)
public class StorageResource {
    private StorageDAO storageDAO;

    public StorageResource(StorageDAO storageDAO){

        this.storageDAO = storageDAO;
    }

    @GET
    @UnitOfWork
    public List<Storage> findAll(){
        return storageDAO.findAll();
    }
}
