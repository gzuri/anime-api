package com.goranzuri.anime;

import com.goranzuri.anime.api.AnimeResource;
import com.goranzuri.anime.healthchecks.DbHealthCheck;
import com.goranzuri.anime.providers.DbProvider;
import com.goranzuri.anime.providers.JsonDbProvider;
import com.goranzuri.anime.service.AnimeService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by gzuri on 20.01.2017..
 */
public class AnimeApiApplication extends Application<AnimeApiConfiguration> {

    @Override
    public void run(AnimeApiConfiguration configuration, Environment environment) throws Exception {
        final DbProvider dbProvider = new JsonDbProvider(configuration);
        final AnimeService animeService = new AnimeService(configuration, dbProvider);
        environment.jersey().register(new AnimeResource(animeService));
        environment.healthChecks().register("database", new DbHealthCheck(configuration));

        //configure CORS
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    public static void main(String[] args) throws Exception {
        new AnimeApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "service-anime";
    }

    @Override
    public void initialize(Bootstrap<AnimeApiConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AnimeApiConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AnimeApiConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

    }
}
