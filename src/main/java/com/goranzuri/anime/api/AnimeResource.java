package com.goranzuri.anime.api;

import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.entities.Anime;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by gzuri on 20.01.2017..
 */

@Path("/anime")
@Produces(MediaType.APPLICATION_JSON)
public class AnimeResource {
    private AnimeDAO animeDAO;

    public AnimeResource(AnimeDAO animeDAO){
        this.animeDAO = animeDAO;
    }

    @GET
    @UnitOfWork
    public List<Anime> findAll() {
        List<Anime> animes = animeDAO.findAll();
        return animes;
    }
}
