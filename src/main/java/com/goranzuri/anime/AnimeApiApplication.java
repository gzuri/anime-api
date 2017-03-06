package com.goranzuri.anime;

import com.goranzuri.anime.api.AnimeResource;
import com.goranzuri.anime.dao.AnimeDAO;
import com.goranzuri.anime.entities.Anime;
import com.goranzuri.anime.entities.AnimeStorage;
import com.goranzuri.anime.entities.Storage;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * Created by gzuri on 20.01.2017..
 */
public class AnimeApiApplication extends Application<AnimeApiConfiguration> {
    private final HibernateBundle<AnimeApiConfiguration> hibernateAnimeBundle = new HibernateBundle<AnimeApiConfiguration>(Anime.class, AnimeStorage.class, Storage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(AnimeApiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }

    };

    @Override
    public void run(AnimeApiConfiguration configuration, Environment environment) throws Exception {
        final AnimeDAO animeDAO = new AnimeDAO(hibernateAnimeBundle.getSessionFactory());
        environment.jersey().register(new AnimeResource(animeDAO));
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
        bootstrap.addBundle(new SwaggerBundle<AnimeApiConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AnimeApiConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

        bootstrap.addBundle(hibernateAnimeBundle);


    }
}
