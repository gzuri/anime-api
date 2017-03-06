package com.goranzuri.anime.api;

import com.codahale.metrics.annotation.Timed;
import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.StorageNotFoundException;
import com.goranzuri.anime.services.AnimeUpdateService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by gzuri on 20.01.2017..
 */
@Api
@Path("/anime")
@Produces(MediaType.APPLICATION_JSON)
public class AnimeResource {
    private AnimeDAO animeDAO;

    public AnimeResource(AnimeDAO animeDAO){
        this.animeDAO = animeDAO;
    }

    @GET
    @UnitOfWork
    @Timed
    public List<Anime> findAll() {
        List<Anime> animes = animeDAO.findAll();
        return animes;
    }

    @Path("/syncList")
    @POST
    @UnitOfWork
    @Timed
    public boolean syncList(@FormParam("storageId") int storageId, @FormParam("animeOnDisk") List<String> animeOnDisk) throws StorageNotFoundException {
        AnimeUpdateService animeUpdateService = new AnimeUpdateService(animeDAO, storageId);

        animeUpdateService.updateListBasedOnStorageContent(animeOnDisk);

        return true;
    }
}
