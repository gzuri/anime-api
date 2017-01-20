package com.goranzuri.anime.entities;

import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import javax.ws.rs.ext.ParamConverter;

/**
 * Created by gzuri on 20.01.2017..
 */
@Entity(name = "anime_storage")

public class AnimeStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anime_storage_id")
    private int animeStorageId;

    @ManyToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;
}
