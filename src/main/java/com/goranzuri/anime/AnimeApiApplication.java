package com.goranzuri.anime;

import com.goranzuri.anime.api.AnimeResource;
import com.goranzuri.anime.api.StorageResource;
import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.dao.StorageDAO;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.entities.Storage;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by gzuri on 20.01.2017..
 */
public class AnimeApiApplication extends Application<AnimeApiConfiguration> {
    private final HibernateBundle<AnimeApiConfiguration> hibernateAnimeBundle = new HibernateBundle<AnimeApiConfiguration>(Anime.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AnimeApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
        @Override
        public String name(){
            return "hibernate.anime";
        }
    };

    private final HibernateBundle<AnimeApiConfiguration> hibernateStorageBundle = new HibernateBundle<AnimeApiConfiguration>(Storage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AnimeApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void run(AnimeApiConfiguration configuration, Environment environment) throws Exception {
        final AnimeDAO animeDAO = new AnimeDAO(hibernateAnimeBundle.getSessionFactory());
        final StorageDAO storageDAO = new StorageDAO(hibernateStorageBundle.getSessionFactory());
        environment.jersey().register(new AnimeResource(animeDAO));
        environment.jersey().register(new StorageResource(storageDAO));
    }

    public static void main(String[] args) throws Exception {
        new AnimeApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "AnimeAPI";
    }

    @Override
    public void initialize(Bootstrap<AnimeApiConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateAnimeBundle);
        bootstrap.addBundle(hibernateStorageBundle);

    }
}
