package com.goranzuri.anime;

import com.goranzuri.anime.exceptions.AnimeNotFoundException;
import com.goranzuri.anime.providers.DbProvider;
import com.goranzuri.anime.providers.InMemoryDbProvider;
import com.goranzuri.anime.service.AniDbService;
import org.junit.Test;


/**
 * Created by gzuri on 15/10/2017.
 */
public class AniDbServiceTest {

  /*  @Test
    public void getAniDbInfo(){
        DbProvider dbProvider = new InMemoryDbProvider();

        AniDbService aniDbService = new AniDbService(dbProvider);


        try {
            aniDbService.getAnidbCode("Ai-Mai-Mi Surgical Friends");

        } catch (AnimeNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
