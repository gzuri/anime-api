package com.goranzuri.anime;

import com.goranzuri.anime.service.AniDbService;
import org.junit.Test;

import java.io.IOException;


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

    @Test
    public void getThumbnail() throws IOException {
        AniDbService aniDbService = new AniDbService();
        aniDbService.getThumbnailUrl("12506");
    }
}
