package com.goranzuri.anime;

import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.exceptions.AnimeNotFoundException;
import com.goranzuri.anime.providers.InMemoryDbProvider;
import com.goranzuri.anime.service.AnimeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by gzuri on 12.02.2017..
 */
public class AnimeServiceTest {
    private final String DEFAULT_ANIME_NAME = "some name";
    private final String DEFAULT_ANIME_LOCATION = "c";

    @Before
    public void init(){

    }

    private Anime createDefaultAnime(){
        Anime anime = new Anime();
        anime.setNameOnDisk(DEFAULT_ANIME_NAME);
        anime.setStorage(Arrays.asList(DEFAULT_ANIME_LOCATION));

        return anime;
    }

    @Test
    public void TestAddNewAnime(){
        AnimeService animeService = new AnimeService(new InMemoryDbProvider());

        animeService.add(createDefaultAnime());

        Assert.assertTrue(animeService.get().size() == 1);
    }

    @Test
    public void TestMergeAnimeWithTwoDifferentStorage() throws AnimeNotFoundException {
        AnimeService animeService = new AnimeService(new InMemoryDbProvider());

        Anime anime = createDefaultAnime();
        animeService.add(anime);

        Anime anime2 = createDefaultAnime();
        anime2.setStorage(Arrays.asList("b"));

        animeService.merge(anime2);
        anime = animeService.get(anime.getAnimeId());

        Assert.assertTrue(anime.getStorage().size() == 2);
    }


    @Test
    public void TestMergeAnimeWithTwoSameStorage() throws AnimeNotFoundException{
        AnimeService animeService = new AnimeService(new InMemoryDbProvider());

        Anime anime = createDefaultAnime();
        animeService.add(anime);

        Anime anime2 = createDefaultAnime();

        animeService.merge(anime2);
        anime = animeService.get(anime.getAnimeId());

        Assert.assertTrue(anime.getStorage().size() == 1);
    }

    @Test
    public void syncDriveWithOneLessAnimeOnStorage(){
        AnimeService animeService = new AnimeService(new InMemoryDbProvider());

        animeService.add(createDefaultAnime());

        Anime anime = createDefaultAnime();
        anime.setNameOnDisk("some name 2");
        animeService.add(anime);

        animeService.syncDrive(Arrays.asList(DEFAULT_ANIME_NAME), DEFAULT_ANIME_LOCATION);


        Long animeOnDefaultStorage = animeService.get().stream().filter(x -> x.getStorage().contains(DEFAULT_ANIME_LOCATION))
                .count();

        Assert.assertTrue(animeOnDefaultStorage == 1);

    }

    @Test
    public void syncDriveNewAnime(){
        AnimeService animeService = new AnimeService(new InMemoryDbProvider());

        Anime anime = createDefaultAnime();
        animeService.add(anime);

        animeService.syncDrive(Arrays.asList(DEFAULT_ANIME_NAME, "some name 2"), DEFAULT_ANIME_LOCATION);

        Assert.assertTrue(animeService.get().size() == 2);
    }
}
