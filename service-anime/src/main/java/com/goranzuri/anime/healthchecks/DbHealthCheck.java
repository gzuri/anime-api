package com.goranzuri.anime.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.goranzuri.anime.AnimeApiConfiguration;

import java.io.File;

/**
 * Created by gzuri on 14/10/2017.
 */
public class DbHealthCheck extends HealthCheck{

    private final AnimeApiConfiguration configuration;

    public DbHealthCheck(AnimeApiConfiguration configuration){

        this.configuration = configuration;
    }

    @Override
    protected Result check() throws Exception {
        File f = new File(configuration.getDbLocation());
        if (f.exists())
            return Result.healthy();

        return Result.unhealthy("db not found");
    }
}
