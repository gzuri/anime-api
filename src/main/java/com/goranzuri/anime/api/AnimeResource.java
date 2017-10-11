package com.goranzuri.anime.api;

import com.codahale.metrics.annotation.Timed;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.service.AnimeService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

/**
 * Created by gzuri on 20.01.2017..
 */
@Api("Anime")
@Path("/anime")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnimeResource {

    private final AnimeService animeService;

    @Inject
    public AnimeResource( AnimeService animeService){
        this.animeService = animeService;
    }

    @GET
    @UnitOfWork
    @Timed
    public List<Anime> findAll() {
        return animeService.get();
    }

    @GET
    @UnitOfWork
    @Timed
    @Path("/{animeId}")
    public Anime get(@PathParam("animeId") UUID id){
        return animeService.get(id);
    }

    @POST
    @Timed
    public Anime post(@Valid Anime anime){
        return animeService.add(anime);
    }
}
